import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AttendanceEntry from './attendance-entry2';
import AttendanceEntryDetail from './attendance-entry-detail';
import AttendanceEntryUpdateEmp from './attendance-entry-update2';
import AttendanceEntryUpdate from './attendance-entry-update';
import AttendanceEntryDeleteDialog from './attendance-entry-delete-dialog';

const AttendanceEntryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AttendanceEntry />} />
    <Route path="new" element={<AttendanceEntryUpdateEmp />} />
    <Route path=":id">
      <Route index element={<AttendanceEntryDetail />} />
      <Route path="edit" element={<AttendanceEntryUpdateEmp />} />
      <Route path="delete" element={<AttendanceEntryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AttendanceEntryRoutes;
