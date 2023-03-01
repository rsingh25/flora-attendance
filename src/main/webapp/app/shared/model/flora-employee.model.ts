import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IFloraEmployee {
  id?: number;
  createdBy?: string | null;
  createdDate?: string | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: string | null;
  attStartTime1?: string | null;
  attEndTime1?: string | null;
  attStartTime2?: string | null;
  internalUser?: IUser;
}

export const defaultValue: Readonly<IFloraEmployee> = {};
