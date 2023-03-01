import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import UserManagement from './user-management';
import UserManagementDetail from './user-management-detail';
import UserManagementUpdate from './user-management-update';
import UserManagementDeleteDialog from './user-management-delete-dialog';
import PasswordPage from './user-management-change-password';
import AttendanceReport from './user-management-attendance-report';

const UserManagementRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserManagement />} />
    <Route path="new" element={<UserManagementUpdate />} />
    <Route path=":login">
      <Route index element={<UserManagementDetail />} />
      <Route path="edit" element={<UserManagementUpdate />} />
      <Route path="delete" element={<UserManagementDeleteDialog />} />
      <Route path="change-password" element={<PasswordPage />} />
      <Route path="attendance-report" element={<AttendanceReport />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserManagementRoutes;
