import dayjs from 'dayjs';

export interface IAttendanceEntry {
  id?: number;
  createdBy?: string | null;
  createdDate?: string | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: string | null;
  yearMonth?: string | null;
  attStart?: string | null;
  attEnd?: string | null;
  comment?: string | null;
}

export const defaultValue: Readonly<IAttendanceEntry> = {};
