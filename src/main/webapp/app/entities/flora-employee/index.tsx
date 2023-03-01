import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FloraEmployee from './flora-employee';
import FloraEmployeeDetail from './flora-employee-detail';
import FloraEmployeeUpdate from './flora-employee-update';
import FloraEmployeeDeleteDialog from './flora-employee-delete-dialog';

const FloraEmployeeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FloraEmployee />} />
    <Route path="new" element={<FloraEmployeeUpdate />} />
    <Route path=":id">
      <Route index element={<FloraEmployeeDetail />} />
      <Route path="edit" element={<FloraEmployeeUpdate />} />
      <Route path="delete" element={<FloraEmployeeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FloraEmployeeRoutes;
