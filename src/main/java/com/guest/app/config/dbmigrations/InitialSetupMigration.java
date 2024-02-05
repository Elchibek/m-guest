package com.guest.app.config.dbmigrations;

import com.github.javafaker.Faker;
import com.guest.app.config.Constants;
import com.guest.app.domain.Authority;
import com.guest.app.domain.Entrance;
import com.guest.app.domain.Floor;
import com.guest.app.domain.Guest;
import com.guest.app.domain.GuestBlock;
import com.guest.app.domain.GuestFrom;
import com.guest.app.domain.GuestHouse;
import com.guest.app.domain.GuestStatic;
import com.guest.app.domain.User;
import com.guest.app.security.AuthoritiesConstants;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.data.mongodb.core.MongoTemplate;

class Gblock {

    private GuestBlock guestBlock;
    private List<Entrance> entrances;
    private List<Floor> floors;
    private List<GuestHouse> guestHouses;

    public GuestBlock getGuestBlock() {
        return guestBlock;
    }

    public void setGuestBlock(GuestBlock guestBlock) {
        this.guestBlock = guestBlock;
    }

    public List<Entrance> getEntrances() {
        return entrances;
    }

    public void setEntrances(List<Entrance> entrances) {
        this.entrances = entrances;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public List<GuestHouse> getGuestHouses() {
        return guestHouses;
    }

    public void setGuestHouses(List<GuestHouse> guestHouses) {
        this.guestHouses = guestHouses;
    }
}

/**
 * Creates the initial database setup.
 */
// docker stop guest-mongodb-1
// docker system prune
// docker compose -f ./src/main/docker/mongodb.yml up -d
// npm run backend:start
@ChangeUnit(id = "users-initialization", order = "001")
public class InitialSetupMigration {

    private final MongoTemplate template;
    private static List<User> users = new ArrayList<>();
    private static List<Gblock> gblocks = new ArrayList<>();
    private static List<GuestFrom> gFroms = new ArrayList<>();

    private static int entranceCount = 0;
    private static int floorCount = 0;
    private static int guestHouseCount = 0;

    public InitialSetupMigration(MongoTemplate template) {
        this.template = template;
    }

    @Execution
    public void changeSet() {
        Authority userAuthority = createUserAuthority();
        userAuthority = template.save(userAuthority);
        Authority adminAuthority = createAdminAuthority();
        adminAuthority = template.save(adminAuthority);
        addUsers(userAuthority, adminAuthority);
        // -----------------------------------
        template.save(createGuestStatic(false, 1));
        template.save(createGuestStatic(true, 2));
        // -----------------------------------
        Faker faker = new Faker();
        addGuestFrom(faker);
        addGuestBlock(faker);
        // addGuest(faker);
    }

    @RollbackExecution
    public void rollback() {}

    private void addGuest(Faker faker) {
        for (int i = 0; i < 15000; i++) {
            createGuest(faker, i);
        }
    }

    private void addGuestFrom(Faker faker) {
        for (int i = 0; i < 5; i++) {
            gFroms.add(createGuestFrom(faker, i));
        }
    }

    private void addGuestBlock(Faker faker) {
        for (int i = 0; i < 10; i++) {
            Gblock gblock = new Gblock();
            List<Entrance> entrances = new ArrayList<>();
            List<Floor> floors = new ArrayList<>();
            List<GuestHouse> guestHouses = new ArrayList<>();
            GuestBlock guestBlock = createGuestBlock(i, faker, entrances, floors, guestHouses);

            gblock.setGuestBlock(guestBlock);
            gblock.setEntrances(entrances);
            gblock.setFloors(floors);
            gblock.setGuestHouses(guestHouses);
            gblocks.add(gblock);
        }
    }

    private void addUsers(Authority userAuthority, Authority adminAuthority) {
        for (int i = 0; i < 300; i++) {
            User user = createUser(userAuthority, i);
            users.add(user);
            template.save(user);
        }
        User user = createAdmin(userAuthority, adminAuthority);
        template.save(user);
    }

    private Authority createAuthority(String authority) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(authority);
        return adminAuthority;
    }

    private Authority createAdminAuthority() {
        Authority adminAuthority = createAuthority(AuthoritiesConstants.ADMIN);
        return adminAuthority;
    }

    private Authority createUserAuthority() {
        Authority userAuthority = createAuthority(AuthoritiesConstants.USER);
        return userAuthority;
    }

    private GuestFrom createGuestFrom(Faker faker, int i) {
        GuestFrom guestFrom = new GuestFrom();
        guestFrom.setId("from-" + i);
        guestFrom.setName(faker.address().cityName());
        template.save(guestFrom);
        return guestFrom;
    }

    private GuestBlock createGuestBlock(int i, Faker faker, List<Entrance> es, List<Floor> fs, List<GuestHouse> hs) {
        Random rand = new Random();
        GuestBlock guestBlock = new GuestBlock();
        guestBlock.setId("gblock-" + i);
        guestBlock.setName(faker.address().cityName());
        guestBlock.setNameEntrance(faker.bothify("?").toUpperCase());
        guestBlock.setNumEntrance(randomNum(rand, 2, 6));
        guestBlock.setNumFloor(randomNum(rand, 4, 20));
        guestBlock.setNameHouse(faker.bothify("?").toUpperCase());
        guestBlock.setNumHouse(randomNum(rand, 2, 5));
        template.save(guestBlock);

        for (int j = 0; j < guestBlock.getNumEntrance(); j++) {
            entranceCount++;
            es.add(creatEntrance(guestBlock, fs, hs, j));
        }
        return guestBlock;
    }

    private Entrance creatEntrance(GuestBlock guestBlock, List<Floor> fs, List<GuestHouse> gs, int index) {
        Entrance entrance = new Entrance();
        entrance.setId("entrance-" + entranceCount);
        entrance.setName(guestBlock.getNameEntrance());
        entrance.setNumEntrance(index + 1); // 3
        entrance.setGuestBlock(guestBlock);
        template.save(entrance);
        for (int i = 0; i < guestBlock.getNumFloor(); i++) {
            floorCount++;
            fs.add(createFloor(entrance, guestBlock, gs, i));
        }
        return entrance;
    }

    private Floor createFloor(Entrance entrance, GuestBlock guestBlock, List<GuestHouse> gs, int index) {
        Floor floor = new Floor();
        floor.setId("floor-" + floorCount);
        floor.setNumFloor(index + 1);
        floor.setEntrance(entrance);
        template.save(floor);

        for (int i = 0; i < guestBlock.getNumHouse(); i++) {
            guestHouseCount++;
            gs.add(createGuestHouse(floor, guestBlock, i));
        }
        return floor;
    }

    private GuestHouse createGuestHouse(Floor floor, GuestBlock guestBlock, int i) {
        GuestHouse guestHouse = new GuestHouse();
        guestHouse.setId("ghouse-" + guestHouseCount);
        guestHouse.setName(guestBlock.getNameHouse());
        guestHouse.setNumHouse(i + 1);
        guestHouse.setIsEmpty(false);
        guestHouse.setCountPerson(0);
        guestHouse.setFloor(floor);
        guestHouse.setGuestBlockId(guestBlock.getId());
        template.save(guestHouse);

        return guestHouse;
    }

    private Guest createGuest(Faker faker, int i) {
        Random rand = new Random();
        Guest guest = new Guest();
        int num = randomNum(rand, 0, gblocks.size() - 1);
        Gblock gb = gblocks.get(num);
        GuestBlock guestBlock = gb.getGuestBlock();
        Entrance entrance = gb.getEntrances().get(randomNum(rand, 0, gb.getEntrances().size() - 1));
        Floor floor = gb.getFloors().get(randomNum(rand, 0, gb.getFloors().size() - 1));
        GuestHouse gHouse = gb.getGuestHouses().get(randomNum(rand, 0, gb.getGuestHouses().size() - 1));

        Instant startDate = Instant.now().plus(randomNum(rand, 5, 60), ChronoUnit.DAYS);
        Instant endDate = startDate.plus(randomNum(rand, 5, 60), ChronoUnit.DAYS);
        LocalDate stD = LocalDate.ofInstant(startDate, ZoneOffset.UTC);
        LocalDate endD = LocalDate.ofInstant(endDate, ZoneOffset.UTC);
        int totalDay = Period.between(stD, endD).getDays();

        guest.setId("guest-" + i);
        guest.setGuestFrom(gFroms.get(randomNum(rand, 0, gFroms.size() - 1)));
        guest.setGuestBlock(guestBlock);
        guest.setEntrance(entrance);
        guest.setFloor(floor);
        guest.setGuestHouse(gHouse);
        guest.setName(faker.address().firstName());
        guest.setGuestInstitution(faker.address().fullAddress());
        guest.setResponsible(faker.address().secondaryAddress());
        guest.setExplanation(faker.address().fullAddress());
        guest.setStartDate(startDate);
        guest.setEndDate(endDate);
        guest.setWillStay(totalDay);
        guest.setNum(totalDay - randomNum(rand, 0, totalDay));
        guest.setIsDeparture(false);
        guest.setCountPerson(randomNum(rand, 5, 100));
        guest.setPrice(randomNum(rand, 5, 1000));
        guest.setCountDidntPay(0);
        guest.setTotalPrice(totalDay * (guest.getPrice() * (guest.getCountPerson() - guest.getCountDidntPay())));
        guest.setIsPaid(true);
        guest.setUser(users.get(randomNum(rand, 0, users.size() - 1)));

        template.save(guest);
        return guest;
    }

    private User createUser(Authority userAuthority, int i) {
        Faker faker = new Faker();
        User userUser = new User();
        userUser.setId("us-" + i);
        userUser.setLogin("user" + i);
        userUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        userUser.setFirstName(faker.address().firstName());
        userUser.setLastName(faker.address().lastName());
        userUser.setEmail(faker.bothify("???????##@gmail.com"));
        userUser.setActivated(true);
        userUser.setLangKey("en");
        userUser.setCreatedBy(Constants.SYSTEM);
        userUser.setCreatedDate(Instant.now());
        userUser.getAuthorities().add(userAuthority);
        return userUser;
    }

    private User createAdmin(Authority adminAuthority, Authority userAuthority) {
        User adminUser = new User();
        adminUser.setId("user-1");
        adminUser.setLogin("admin");
        adminUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        adminUser.setFirstName("admin");
        adminUser.setLastName("Administrator");
        adminUser.setEmail("admin@localhost");
        adminUser.setActivated(true);
        adminUser.setLangKey("en");
        adminUser.setCreatedBy(Constants.SYSTEM);
        adminUser.setCreatedDate(Instant.now());
        adminUser.getAuthorities().add(adminAuthority);
        adminUser.getAuthorities().add(userAuthority);
        return adminUser;
    }

    private GuestStatic createGuestStatic(boolean isArhive, int id) {
        GuestStatic guestStatic = new GuestStatic();
        guestStatic.setId("gueststatic" + id);
        guestStatic.setIsArchive(isArhive);
        guestStatic.setCountPerson(0);
        guestStatic.setTotalPrice(0);
        guestStatic.setAffordableApartments(0);
        guestStatic.setNumOfApartments(0);
        guestStatic.setTotalDidntPay(0);
        return guestStatic;
    }

    private int randomNum(Random rand, int min, int max) {
        return min + rand.nextInt((max - min) + 1);
    }
}
