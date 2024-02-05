export interface ITabkey {
  name?: string | null;
  fieldName?: string | null;
  translate?: string | null;
  sortBy?: string | null;
  selected?: boolean | null;
}

export const tabKeys: ITabkey[] = [
  {
    name: 'ID',
    fieldName: 'id',
    translate: 'global.field.id',
    sortBy: null,
    selected: true,
  },
  {
    name: 'Guest From',
    fieldName: 'guestFrom',
    translate: 'guestApp.guest.guestFrom',
    sortBy: null,
    selected: true,
  },
  {
    name: 'Guest Block',
    fieldName: 'guestBlock',
    translate: 'guestApp.guest.guestBlock',
    sortBy: null,
    selected: true,
  },
  {
    name: 'Entrance',
    fieldName: 'entrance',
    translate: 'guestApp.guest.entrance',
    sortBy: null,
    selected: true,
  },
  {
    name: 'Floor',
    fieldName: 'floor',
    translate: 'guestApp.guest.floor',
    sortBy: null,
    selected: true,
  },
  {
    name: 'Guest House',
    fieldName: 'guestHouse',
    translate: 'guestApp.guest.guestHouse',
    sortBy: null,
    selected: true,
  },
  {
    name: 'Name',
    fieldName: 'name',
    translate: 'guestApp.guest.name',
    sortBy: null,
    selected: true,
  },
  {
    name: 'Guest Institution',
    fieldName: 'guestInstitution',
    translate: 'guestApp.guest.guestInstitution',
    sortBy: null,
    selected: true,
  },
  {
    name: 'Responsible',
    fieldName: 'responsible',
    translate: 'guestApp.guest.responsible',
    sortBy: null,
    selected: true,
  },
  {
    name: 'Explanation',
    fieldName: 'explanation',
    translate: 'guestApp.guest.explanation',
    sortBy: null,
    selected: true,
  },
  {
    name: 'Start Date',
    fieldName: 'startDate',
    translate: 'guestApp.guest.startDate',
    sortBy: 'startDate',
    selected: true,
  },
  {
    name: 'End Date',
    fieldName: 'endDate',
    translate: 'guestApp.guest.endDate',
    sortBy: 'endDate',
    selected: true,
  },
  {
    name: 'Rest Of The Day',
    fieldName: 'restOfTheDay',
    translate: 'guestApp.guest.restOfTheDay',
    sortBy: 'restOfTheDay.id',
    selected: true,
  },
  {
    name: 'Count Person',
    fieldName: 'countPerson',
    translate: 'guestApp.guest.countPerson',
    sortBy: 'countPerson',
    selected: true,
  },
  {
    name: 'Price',
    fieldName: 'price',
    translate: 'guestApp.guest.price',
    sortBy: 'price',
    selected: true,
  },
  {
    name: 'Total Price',
    fieldName: 'totalPrice',
    translate: 'guestApp.guest.totalPrice',
    sortBy: 'totalPrice',
    selected: true,
  },
  {
    name: 'User',
    fieldName: 'createdBy',
    translate: 'guestApp.guest.createdBy',
    sortBy: null,
    selected: true,
  },
];
