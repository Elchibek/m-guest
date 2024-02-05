package com.guest.app.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.guest.app.domain.Guest;
import com.guest.app.domain.User;
import com.guest.app.repository.GuestRepository;
import com.guest.app.repository.PutAwayRepository;
import com.guest.app.security.SecurityUtils;
import com.guest.app.service.dto.ArrivalDepartureStaticDTO;
import com.guest.app.service.dto.DidntPayDTO;
import com.guest.app.service.dto.GuestContactDTO;
import com.guest.app.service.dto.GuestDTO;
import com.guest.app.service.dto.GuestFilterDTO;
import com.guest.app.service.dto.GuestHouseDTO;
import com.guest.app.service.dto.GuestStaticDTO;
import com.guest.app.service.dto.PutAwayDTO;
import com.guest.app.service.dto.docs.Doc;
import com.guest.app.service.dto.docs.TabKey;
import com.guest.app.service.mapper.GuestMapper;
import com.spire.doc.*;
import com.spire.doc.documents.*;
import com.spire.doc.fields.TextRange;
import jakarta.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.guest.app.domain.Guest}.
 */
@Service
public class GuestService {

    private final Logger log = LoggerFactory.getLogger(GuestService.class);

    private Path foundFile = null;

    private final MongoTemplate mt;

    private final GuestRepository guestRepository;

    private final GuestHouseService guestHouseService;

    private final UserService userService;

    private final HttpServletRequest request;

    private final GuestStaticService guestStaticService;

    private final PutAwayService putAwayService;

    private final GuestContactService guestContactService;

    private final DidntPayService didntPayService;

    private final ArrivalDepartureStaticService arrivalDepartureStaticService;

    private final GuestMapper guestMapper;

    public GuestService(
        MongoTemplate mongoTemplate,
        GuestRepository guestRepository,
        GuestMapper guestMapper,
        UserService userService,
        HttpServletRequest request,
        GuestHouseService guestHouseService,
        GuestStaticService guestStaticService,
        PutAwayRepository putAwayRepository,
        GuestContactService guestContactService,
        PutAwayService putAwayService,
        DidntPayService didntPayService,
        ArrivalDepartureStaticService arrivalDepartureStaticService
    ) {
        this.mt = mongoTemplate;
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
        this.guestHouseService = guestHouseService;
        this.userService = userService;
        this.request = request;
        this.guestStaticService = guestStaticService;
        this.putAwayService = putAwayService;
        this.guestContactService = guestContactService;
        this.didntPayService = didntPayService;
        this.arrivalDepartureStaticService = arrivalDepartureStaticService;
    }

    public GuestDTO save(GuestDTO guestDTO) {
        log.debug("Request to save Guest : {}", guestDTO);
        Guest guest = getDidntPay(guestDTO, false, false, false);
        return guestMapper.toDto(guest);
    }

    private void setContacts(GuestDTO guest, List<GuestContactDTO> contacts) {
        if (contacts != null) {
            contacts.forEach(c -> {
                if (c != null) {
                    boolean isDelete = c.getIsDelete() != null && c.getIsDelete();
                    if (isNotNull(c.getId()) && isDelete) {
                        guestContactService.delete(c.getId());
                    } else if (!isDelete && (isNotNull(c.getName()) || isNotNull(c.getPhone()))) {
                        c.setGuest(guest);
                        guestContactService.save(c);
                    }
                }
            });
        }
    }

    private Guest getDidntPay(GuestDTO guestDTO, boolean isPartialUpdate, boolean isArchive, boolean isDelete) {
        boolean isSave = isNotNull(guestDTO.getId()) ? false : true;
        User user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        Guest prevGuest = isSave ? null : findOneNotDto(guestDTO.getId()).get();
        Guest guest = guestMapper.toEntity(guestDTO);

        int totalDay = period(guest.getStartDate(), guest.getEndDate());
        guest.setTotalPrice(totalDay * (guest.getPrice() * guest.getCountPerson()));

        restOfTheDay(guest, totalDay, isSave);
        guestNotPaid(prevGuest, guest, isSave);

        if (!isSave && !isNotNull(prevGuest.getParentId())) {
            if (isPartialUpdate) guestMapper.partialUpdate(guest, guestDTO);
            guestChange(guest, prevGuest);
        }

        guest.setUser(user);
        GuestDTO GDTO = guestMapper.toDto(guest);
        // setPutAways(guest, GDTO, guestDTO.getPutAways());
        guest = guestRepository.save(guest);

        setGuestStatic(prevGuest, guest, isSave, isDelete, isArchive);
        // setDepartueStatic(prevGuest, guest, isSave, isDelete, isArchive);
        setHouse(prevGuest, guest, isSave, isDelete);
        setContacts(GDTO, guestDTO.getContacts());
        return guest;
    }

    private void setPutAways(Guest guest, GuestDTO guestDTO, List<PutAwayDTO> putAways) {
        if (putAways != null) {
            int countPerson = 0;
            for (int i = 0; i < putAways.size(); i++) {
                PutAwayDTO d = putAways.get(i);
                if (d != null) {
                    boolean isDelete = d.getIsDelete() != null && d.getIsDelete();
                    if (isNotNull(d.getId()) && isDelete) {
                        didntPayService.delete(d.getId());
                    } else if (!isDelete && d.getCountPerson() != null) {
                        countPerson += d.getCountPerson();
                        d.setGuest(guestDTO);
                        putAwayService.save(d);
                    }
                }
            }
            guest.setCountPerson(0);
        }
    }

    private void guestNotPaid(Guest pg, Guest guest, boolean isSave) {
        if (
            !guest.getIsPaid() &&
            (guest.getCountDidntPay() != null && guest.getCountDidntPay() > 0) &&
            (guest.getDidntPay() != null && guest.getDidntPay() > 0)
        ) {
            int totalPrice = 0;

            if (guest.getCountDidntPay() > guest.getCountPerson()) guest.setCountDidntPay(guest.getCountPerson());

            if (guest.getDidntPay() > guest.getTotalPrice()) totalPrice = 0; else totalPrice = guest.getTotalPrice() - guest.getDidntPay();

            guest.setTotalPrice(totalPrice);
            guest.setIsPaid(false);
        } else {
            if (!isSave && pg.getCountDidntPay() > 0 && pg.getDidntPay() > 0) {
                guest.setTotalPrice(guest.getTotalPrice() + pg.getDidntPay());
            }
            guest.setCountDidntPay(0);
            guest.setDidntPay(0);
            guest.setIsPaid(true);
        }
    }

    private void guestChange(Guest g, Guest p) {
        Guest guest = new Guest();
        int count = 0;
        if (!g.getGuestFrom().getId().equals(p.getGuestFrom().getId())) {
            guest.setGuestFrom(g.getGuestFrom());
            count++;
        }
        if (!g.getGuestBlock().getId().equals(p.getGuestBlock().getId())) {
            guest.setGuestBlock(g.getGuestBlock());
            count++;
        }
        if (!g.getEntrance().getId().equals(p.getEntrance().getId())) {
            guest.setEntrance(g.getEntrance());
            count++;
        }
        if (!g.getFloor().getId().equals(p.getFloor().getId())) {
            guest.setFloor(g.getFloor());
            count++;
        }
        if (!g.getGuestHouse().getId().equals(p.getGuestHouse().getId())) {
            guest.setGuestHouse(g.getGuestHouse());
            count++;
        }
        if (isEquals(g.getName(), p.getName())) {
            guest.setName(g.getName());
            count++;
        }
        if (isEquals(g.getGuestInstitution(), p.getGuestInstitution())) {
            guest.setGuestInstitution(g.getGuestInstitution());
            count++;
        }
        if (isEquals(g.getResponsible(), p.getResponsible())) {
            guest.setResponsible(g.getResponsible());
            count++;
        }
        if (isEquals(g.getExplanation(), p.getExplanation())) {
            guest.setExplanation(g.getExplanation());
            count++;
        }
        if (g.getStartDate().toEpochMilli() != p.getStartDate().toEpochMilli()) {
            guest.setStartDate(g.getStartDate());
            count++;
        }
        if (g.getEndDate().toEpochMilli() != p.getEndDate().toEpochMilli()) {
            guest.setEndDate(g.getEndDate());
            count++;
        }
        if (g.getCountPerson() != p.getCountPerson()) {
            guest.setCountPerson(g.getCountPerson());
            count++;
        }
        if (g.getPrice() != p.getPrice()) {
            guest.setPrice(g.getPrice());
            count++;
        }
        if (g.getCountDidntPay() != p.getCountDidntPay()) {
            guest.setCountDidntPay(g.getCountDidntPay());
            count++;
        }

        if (count > 0) {
            g.setIsUpdate(true);
            boolean isParent = guestRepository.findFirstByParentId(g.getId()).isPresent();
            guest.setParentId(g.getId());
            guest.setIsChange(true);
            if (!isParent) {
                p.setId(null);
                p.setParentId(g.getId());
                p.setIsChange(true);
                guestRepository.save(p);
            }
            guestRepository.save(guest);
        }
    }

    public GuestDTO update(GuestDTO guestDTO) {
        log.debug("Request to update Guest : {}", guestDTO);
        Guest guest = getDidntPay(guestDTO, false, false, false);
        return guestMapper.toDto(guest);
    }

    public Optional<GuestDTO> partialUpdate(GuestDTO guestDTO) {
        log.debug("Request to partially update Guest : {}", guestDTO);
        Guest guest = getDidntPay(guestDTO, true, false, false);
        return Optional.of(guest).map(guestMapper::toDto);
    }

    public Resource downloadDoc(String typeDoc) throws IOException {
        Path dirPath = Paths.get(request.getServletContext().getRealPath("/content"));
        Files
            .list(dirPath)
            .forEach(file -> {
                if (file.getFileName().toString().startsWith("GuestTableWithData" + typeDoc)) {
                    foundFile = file;
                    return;
                }
            });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }
        return null;
    }

    public void saveDoc(Doc doc) {
        List<TabKey> headers = doc.getTabKeys().stream().filter(h -> h.getSelected()).toList();
        List<Guest> guests = findAll(doc.getGuestFilter(), Pageable.unpaged()).getContent();

        String realPathtoUploads = request.getServletContext().getRealPath("/content");

        // Create a Document object
        Document document = new Document();
        // Add a section
        Section section = document.addSection();

        // Add a table
        Table table = section.addTable(true);
        table.resetCells(guests.size() + 1, headers.size());

        // Установите первую строку в качестве заголовка таблицы
        TableRow row = table.getRows().get(0);
        row.isHeader(true);
        // row.setHeight(25);
        // row.setHeightType(TableRowHeightType.Exactly);
        // row.getRowFormat().setBackColor(Color.DARK_GRAY);

        for (int i = 0; i < headers.size(); i++) {
            // row.getCells().get(i).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
            Paragraph p = row.getCells().get(i).addParagraph();
            // p.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
            TextRange txtRange = p.appendText(headers.get(i).getName());
            // txtRange.getCharacterFormat().setBold(true);
            // txtRange.getCharacterFormat().setTextColor(Color.WHITE);
        }

        // Добавьте данные в остальные строки
        for (int r = 0; r < guests.size(); r++) {
            TableRow dataRow = table.getRows().get(r + 1);
            // dataRow.setHeight(22);
            // dataRow.setHeightType(TableRowHeightType.Exactly);
            // dataRow.getRowFormat().setBackColor(Color.white);
            for (int c = 0; c < headers.size(); c++) {
                Paragraph p = dataRow.getCells().get(c).addParagraph();
                dataRow.getCells().get(c).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
                switch (headers.get(c).getFieldName()) {
                    case "guestFrom":
                        p.appendText(guests.get(r).getGuestFrom().getName());
                        break;
                    case "guestBlock":
                        p.appendText(guests.get(r).getGuestBlock().getName());
                        break;
                    case "entrance":
                        p.appendText(guests.get(r).getEntrance().getName() + guests.get(r).getEntrance().getNumEntrance());
                        break;
                    case "floor":
                        p.appendText(guests.get(r).getEntrance().getNumEntrance() + "");
                        break;
                    case "guestHouse":
                        p.appendText(guests.get(r).getGuestHouse().getName() + guests.get(r).getGuestHouse().getNumHouse());
                        break;
                    case "name":
                        p.appendText(guests.get(r).getName());
                        break;
                    case "guestInstitution":
                        p.appendText(guests.get(r).getGuestInstitution());
                        break;
                    case "responsible":
                        p.appendText(guests.get(r).getResponsible());
                        break;
                    case "explanation":
                        p.appendText(guests.get(r).getExplanation());
                        break;
                    case "startDate":
                        p.appendText(guests.get(r).getStartDate().toString());
                        break;
                    case "endDate":
                        p.appendText(guests.get(r).getEndDate().toString());
                        break;
                    case "restOfTheDay":
                        break;
                    case "countPerson":
                        p.appendText(String.valueOf(guests.get(r).getCountPerson()));
                        break;
                    case "price":
                        p.appendText(String.valueOf(guests.get(r).getPrice()));
                        break;
                    case "totalPrice":
                        p.appendText(String.valueOf(guests.get(r).getTotalPrice()));
                        break;
                    case "createdBy":
                        p.appendText(guests.get(r).getUser().getFirstName() + guests.get(r).getUser().getLastName());
                        break;
                }
            }
        }

        // Set background color for cells
        for (int j = 1; j < table.getRows().getCount(); j++) {
            if (j % 2 == 0) {
                TableRow row2 = table.getRows().get(j);
                for (int f = 0; f < row2.getCells().getCount(); f++) {
                    row2.getCells().get(f).getCellFormat().setBackColor(new Color(191, 191, 191));
                    row2.getCells().get(f).setCellWidth(160f, CellWidthType.Point);
                }
            }
            // for (int i = 0; i < headers.size(); i++) {
            //     // Установите ширину столбцов
            //     table.set;
            // }
        }
        table.setDefaultColumnWidth(200f);
        table.autoFit(AutoFitBehaviorType.Auto_Fit_To_Contents);

        FileFormat fileFormat = FileFormat.Docx_2013;
        String type = "docx";
        if (doc.getDocType().equalsIgnoreCase("docx")) {
            fileFormat = FileFormat.Docx_2013;
            type = ".docx";
        } else if (doc.getDocType().equalsIgnoreCase("pdf")) {
            fileFormat = FileFormat.PDF;
            type = ".pdf";
        }

        // Save to file
        document.saveToFile(realPathtoUploads + "/GuestTableWithData" + type, fileFormat);
    }

    public Page<Guest> search(String textQuery, Pageable pageable) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(textQuery);
        Query query = TextQuery.queryText(criteria).sortByScore();
        query.with(pageable);
        List<Guest> guests = mt.find(query, Guest.class, "guest");
        long count = mt.count(query, Guest.class);
        return PageableExecutionUtils.getPage(guests, pageable, () -> count);
    }

    public Page<Guest> findAll(GuestFilterDTO g, Pageable pageable) {
        log.debug("Request to get all Guests");
        Query query = new Query();

        // Default Criteria
        query.addCriteria(Criteria.where("isDeleted").is(g.getIsDeleted()));
        query.addCriteria(Criteria.where("isChange").is(g.getIsChange()));
        query.addCriteria(Criteria.where("isArchive").is(g.getIsArchive()));
        if (g.getParentId() == null) {
            if (g.getIsUpdate() != null) {
                query.addCriteria(Criteria.where("isUpdate").is(g.getIsUpdate()));
            }
            if (g.getUserId() != null) {
                query.addCriteria(Criteria.where("user.id").is(g.getUserId()));
            }
            if (g.getGuestFromId() != null) {
                query.addCriteria(Criteria.where("guestFrom.id").is(g.getGuestFromId()));
            }
            if (g.getGuestBlockId() != null) {
                query.addCriteria(Criteria.where("guestBlock.id").is(g.getGuestBlockId()));
            }
            if (g.getEntranceId() != null) {
                query.addCriteria(Criteria.where("entrance.id").is(g.getEntranceId()));
            }
            if (g.getFloorId() != null) {
                query.addCriteria(Criteria.where("floor.id").is(g.getFloorId()));
            }
            if (g.getHouseId() != null) {
                query.addCriteria(Criteria.where("house.id").is(g.getHouseId()));
            }
            if (g.getIsPaid() != null) {
                query.addCriteria(Criteria.where("paid").is(g.getIsPaid()));
            }
            if (g.getIsDeparture() != null) {
                query.addCriteria(Criteria.where("isDeparture").is(g.getIsDeparture()));
            }
            if (g.getTypeDate() != null) {
                if (g.getStartDate() != null && g.getEndDate() != null) {
                    if (g.getTypeDate().equals("between")) {
                        query.addCriteria(Criteria.where("startDate").gte(g.getStartDate()).and("endDate").lte(g.getEndDate()));
                    } else if (g.getTypeDate().equals("betweenStartDate")) {
                        query.addCriteria(Criteria.where("startDate").gte(g.getStartDate()).lte(g.getEndDate()));
                    } else if (g.getTypeDate().equals("betweenEndDate")) {
                        query.addCriteria(Criteria.where("endDate").gte(g.getStartDate()).lte(g.getEndDate()));
                    }
                }
            }
            if (g.getTypeBetween() != null) {
                String type = "price";
                if (g.getTypeBetween().equals("betweenPesrson")) {
                    type = "countPerson";
                } else if (g.getTypeBetween().equals("betweenPrice")) {
                    type = "price";
                } else if (g.getTypeBetween().equals("betweenTotalPrice")) {
                    type = "totalPrice";
                } else if (g.getTypeBetween().equals("betweenRestOfTheDay")) {
                    type = "num";
                }

                if (g.getFrom() != null && g.getBefore() != null) {
                    query.addCriteria(Criteria.where(type).gte(g.getFrom()).lte(g.getBefore()));
                } else if (g.getFrom() != null && g.getBefore() == null) {
                    query.addCriteria(Criteria.where(type).gte(g.getFrom()));
                } else if (g.getBefore() != null && g.getFrom() == null) {
                    query.addCriteria(Criteria.where(type).lte(g.getBefore()));
                }
            }
        } else {
            query.addCriteria(Criteria.where("parentId").is(g.getParentId()));
        }
        long count = mt.count(query, Guest.class);
        query.with(pageable);

        List<Guest> guests = mt.find(query, Guest.class, "guest");
        return PageableExecutionUtils.getPage(guests, pageable, () -> count);
    }

    public List<Guest> findStatic(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Request to get all Guests");
        // Criteria criteria = ;
        Aggregation agg = newAggregation(
            match(Criteria.where("startDate").is(startDate))
            // group("startDate").sum("count").as("countPerson")
        );
        AggregationResults<Guest> results = mt.aggregate(agg, "guest", Guest.class);
        List<Guest> mappedResult = results.getMappedResults();
        log.debug("Guest - " + mappedResult);
        return mappedResult;
    }

    public Optional<GuestDTO> findOne(String id) {
        // log.debug("Request to get Guest : {}", id);
        return guestRepository
            .findById(id)
            .map(g -> {
                GuestDTO guestDTO = guestMapper.toDto(g);
                List<GuestContactDTO> contacts = guestContactService.findAllByGuestId(id).getContent();

                guestDTO.setContacts(contacts);
                return guestDTO;
            });
    }

    public Optional<Guest> findOneNotDto(String id) {
        log.debug("Request to get Guest : {}", id);
        return guestRepository.findById(id);
    }

    public Long countGuestBlock(String guestBlockId) {
        return guestRepository.countGuestBlock(guestBlockId);
    }

    public void delete(String id, boolean isRestore) {
        log.debug("Request to delete Guest : {}", id);
        Guest guest = findOneNotDto(id).get();
        if (!guest.getIsArchive()) {
            if ((!guest.getIsChange() && !guest.getIsDeleted()) || isRestore) {
                boolean isSave = false, isDelete = true;
                if (isRestore) {
                    isSave = true;
                    isDelete = false;
                }
                setHouse(null, guest, isSave, isDelete);
                setGuestStatic(null, guest, isSave, isDelete, guest.getIsArchive());

                guest.setIsDeleted(isDelete);
                guestRepository.save(guest);
                if (guest.getIsUpdate()) {
                    mt.updateMulti(
                        Query.query(where("parentId").is(guest.getId())),
                        Update.update("isDeleted", isDelete),
                        Guest.class,
                        "guest"
                    );
                }
            } else if (!guest.getIsChange()) {
                if (guest.getIsDeleted()) {
                    // Бул жерди жакшылап караш керек анткени for колдонушу мумкун
                    // putAwayRepository.deleteByGuestId(guest.getId());
                    guestContactService.deleteByGuestId(guest.getId());
                    didntPayService.deleteByGuestId(guest.getId());
                    guestRepository.deleteByParentId(guest.getId());
                }
                guestRepository.deleteById(guest.getId());
            }
        }
    }

    public void archive(String id) {
        Guest guest = findOneNotDto(id).get();
        if (guest != null && !guest.getIsDeleted() && !guest.getIsChange()) {
            guest.setIsArchive(true);
            guestRepository.save(guest);
            setHouse(null, guest, false, true);
            setGuestStatic(null, guest, false, true, false);
            setGuestStatic(null, guest, true, false, true);
        }
    }

    private void setDepartueStatic(Guest prevGuest, Guest guest, boolean isSave, boolean isDelete, boolean isArhive) {
        ArrivalDepartureStaticDTO start = arrivalDepartureStaticService.findOneStartDate(guest.getStartDate()).get();
        ArrivalDepartureStaticDTO end = arrivalDepartureStaticService.findOneEndDate(guest.getEndDate()).get();
        int startCountPerson = 0;
        int endCountPerson = 0;

        if (!isDelete && isSave) {
            if (start != null) startCountPerson = start.getStartCountPerson() + guest.getCountPerson(); else {
                start = new ArrivalDepartureStaticDTO();
                start.setStartDate(guest.getStartDate());
            }

            if (end != null) endCountPerson = end.getEndCountPerson() + guest.getCountPerson(); else {
                end = new ArrivalDepartureStaticDTO();
                end.setStartDate(guest.getEndDate());
            }
        } else if (!isDelete && !isSave && prevGuest != null) {
            if (guest.getStartDate().toEpochMilli() != prevGuest.getStartDate().toEpochMilli()) {
                ArrivalDepartureStaticDTO prevStart = arrivalDepartureStaticService.findOneStartDate(prevGuest.getStartDate()).get();
                prevStart.setStartCountPerson(prevStart.getStartCountPerson() - prevGuest.getCountPerson());
                arrivalDepartureStaticService.save(prevStart);
            }
            if (guest.getEndDate().toEpochMilli() != prevGuest.getEndDate().toEpochMilli()) {
                ArrivalDepartureStaticDTO prevEnd = arrivalDepartureStaticService.findOneEndDate(prevGuest.getEndDate()).get();
                prevEnd.setEndCountPerson(prevEnd.getEndCountPerson() - prevGuest.getCountPerson());
                arrivalDepartureStaticService.save(prevEnd);
            }
            startCountPerson = calcStatic(start.getStartCountPerson(), prevGuest.getCountPerson(), guest.getCountPerson());
            endCountPerson = calcStatic(end.getEndCountPerson(), prevGuest.getCountPerson(), guest.getCountPerson());
        } else if (isDelete) {
            startCountPerson -= guest.getCountPerson();
            endCountPerson -= guest.getCountPerson();
        }

        start.setStartCountPerson(startCountPerson);
        end.setEndCountPerson(endCountPerson);

        arrivalDepartureStaticService.save(start);
        arrivalDepartureStaticService.save(end);
    }

    private void setGuestStatic(Guest prevGuest, Guest guest, boolean isSave, boolean isDelete, boolean isArhive) {
        GuestStaticDTO gStatic = guestStaticService.findOne(isArhive).get();

        if (gStatic != null) {
            int countPerson = gStatic.getCountPerson(); // 0 -> 10
            int totalPrice = gStatic.getTotalPrice();
            int totalDidntPay = gStatic.getTotalDidntPay();

            if (!isDelete && isSave && prevGuest == null) {
                countPerson += guest.getCountPerson();
                totalPrice += guest.getTotalPrice();
                if (guest.getCountDidntPay() != null) totalDidntPay += guest.getCountDidntPay();
            } else if (!isDelete && !isSave && prevGuest != null) {
                countPerson = calcStatic(countPerson, prevGuest.getCountPerson(), guest.getCountPerson());
                totalPrice = calcStatic(totalPrice, prevGuest.getTotalPrice(), guest.getTotalPrice());
                totalDidntPay = calcStatic(totalDidntPay, prevGuest.getCountDidntPay(), guest.getCountDidntPay());
            } else if (isDelete) {
                countPerson -= guest.getCountPerson();
                totalPrice -= guest.getTotalPrice();
                totalDidntPay -= guest.getCountDidntPay();
            }

            gStatic.setCountPerson(countPerson <= 0 ? 0 : countPerson);
            gStatic.setTotalPrice(totalPrice <= 0 ? 0 : totalPrice);
            gStatic.setTotalDidntPay(totalDidntPay <= 0 ? 0 : totalDidntPay);
            guestStaticService.save(gStatic);
        }
    }

    private void setHouse(Guest prevGuest, Guest guest, boolean isSave, boolean isDelte) {
        if (guest != null && guest.getGuestHouse() != null) {
            String houseId = guest.getGuestHouse().getId();

            GuestHouseDTO gHouseDTO = guestHouseService.findOne(houseId).get();
            if (gHouseDTO != null) {
                String prevHouseId = null;
                int count = 0;
                if (!isDelte && !isSave) prevHouseId = prevGuest.getGuestHouse().getId();

                if (!isSave && (isNotNull(prevHouseId) && !houseId.equalsIgnoreCase(prevHouseId))) {
                    GuestHouseDTO gh = guestHouseService.findOne(prevHouseId).get();
                    count = gh.getCountPerson() - prevGuest.getCountPerson();
                    gh.setCountPerson(count);
                    gh.setIsEmpty(count <= 0 ? false : true);
                    guestHouseService.partialUpdate(gh);
                    isSave = true;
                }

                count = gHouseDTO.getCountPerson();
                if (isSave && !isDelte) count += guest.getCountPerson(); else if (!isSave && !isDelte) count =
                    calcStatic(count, prevGuest.getCountPerson(), guest.getCountPerson()); else if (isDelte) count =
                    count - guest.getCountPerson();

                gHouseDTO.setCountPerson(count <= 0 ? 0 : count);
                gHouseDTO.setIsEmpty(count <= 0 ? false : true);
                guestHouseService.partialUpdate(gHouseDTO);
            }
        }
    }

    private int calcStatic(int count, int prev, int orig) {
        if (prev < orig) {
            count += Math.abs(prev - orig);
        } else if (prev > orig) {
            count -= (prev - orig);
        }
        return count;
    }

    private int period(Instant startDate, Instant endDate) {
        LocalDate stD = LocalDate.ofInstant(startDate, ZoneOffset.UTC);
        LocalDate endD = LocalDate.ofInstant(endDate, ZoneOffset.UTC);
        return Period.between(stD, endD).getDays();
    }

    private void restOfTheDay(Guest guest, int totalDay, boolean isSave) {
        int num = 1; // Бул 1'дин себеби setIsDeparture
        if (!isSave) {
            int previousTotalDay = guest.getWillStay();
            int restOfDay = guest.getNum();
            int livedDays = previousTotalDay - restOfDay;
            num = totalDay - livedDays;
        }
        guest.setWillStay(totalDay);
        guest.setNum(isSave ? totalDay : num);
        guest.setIsDeparture((num <= 0) ? true : false);
    }

    private boolean isNotNull(String val) {
        return (val != null) && !val.isEmpty();
    }

    private boolean isEquals(String v1, String v2) {
        if (v2 == null && v1 != null) {
            return true;
        } else if ((v1 != null && v2 != null) && !v1.equals(v2)) {
            return true;
        }
        return false;
    }
}
