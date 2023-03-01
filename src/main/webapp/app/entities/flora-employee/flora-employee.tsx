import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFloraEmployee } from 'app/shared/model/flora-employee.model';
import { getEntities } from './flora-employee.reducer';

export const FloraEmployee = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const floraEmployeeList = useAppSelector(state => state.floraEmployee.entities);
  const loading = useAppSelector(state => state.floraEmployee.loading);
  const totalItems = useAppSelector(state => state.floraEmployee.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="flora-employee-heading" data-cy="FloraEmployeeHeading">
        Flora Employees
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/flora-employee/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Flora Employee
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {floraEmployeeList && floraEmployeeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  Created By <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  Created Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastModifiedBy')}>
                  Last Modified By <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastModifiedDate')}>
                  Last Modified Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attStartTime1')}>
                  Att Start Time 1 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attEndTime1')}>
                  Att End Time 1 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attStartTime2')}>
                  Att Start Time 2 <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Internal User <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {floraEmployeeList.map((floraEmployee, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/flora-employee/${floraEmployee.id}`} color="link" size="sm">
                      {floraEmployee.id}
                    </Button>
                  </td>
                  <td>{floraEmployee.createdBy}</td>
                  <td>
                    {floraEmployee.createdDate ? (
                      <TextFormat type="date" value={floraEmployee.createdDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{floraEmployee.lastModifiedBy}</td>
                  <td>
                    {floraEmployee.lastModifiedDate ? (
                      <TextFormat type="date" value={floraEmployee.lastModifiedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {floraEmployee.attStartTime1 ? (
                      <TextFormat type="date" value={floraEmployee.attStartTime1} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {floraEmployee.attEndTime1 ? (
                      <TextFormat type="date" value={floraEmployee.attEndTime1} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {floraEmployee.attStartTime2 ? (
                      <TextFormat type="date" value={floraEmployee.attStartTime2} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{floraEmployee.internalUser ? floraEmployee.internalUser.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/flora-employee/${floraEmployee.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/flora-employee/${floraEmployee.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/flora-employee/${floraEmployee.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Flora Employees found</div>
        )}
      </div>
      {totalItems ? (
        <div className={floraEmployeeList && floraEmployeeList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default FloraEmployee;
