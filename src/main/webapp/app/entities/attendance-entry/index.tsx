import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AttendanceEntry from './attendance-entry';
import AttendanceEntryDetail from './attendance-entry-detail';
import AttendanceEntryUpdate from './attendance-entry-update';
import AttendanceEntryDeleteDialog from './attendance-entry-delete-dialog';

const AttendanceEntryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AttendanceEntry />} />
    <Route path="new" element={<AttendanceEntryUpdate />} />
    <Route path=":id">
      <Route index element={<AttendanceEntryDetail />} />
      <Route path="edit" element={<AttendanceEntryUpdate />} />
      <Route path="delete" element={<AttendanceEntryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AttendanceEntryRoutes;
