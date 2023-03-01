import dayjs from 'dayjs';

import { APP_LOCAL_DATETIME_FORMAT } from 'app/config/constants';

export const convertDateTimeFromServer = date => (date ? dayjs(date).format(APP_LOCAL_DATETIME_FORMAT) : null);

export const convertDateTimeToServer = date => (date ? dayjs(date).toDate() : null);

export const displayDefaultDateTime = () => dayjs().startOf('day').format(APP_LOCAL_DATETIME_FORMAT);

export const displayNow = () => dayjs().format(APP_LOCAL_DATETIME_FORMAT);

export const yyyyMm = () => dayjs().format('YYYY-MM');

export const diffInHour = (start, end) => Number(dayjs(end).diff(dayjs(start), 'hours', true).toFixed(2));
