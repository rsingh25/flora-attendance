import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { diffInHour } from 'app/shared/util/date-utils';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAttendanceEntry } from 'app/shared/model/attendance-entry.model';
import { getEntities } from './attendance-entry.reducer';

export const AttendanceEntry = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const attendanceEntryList = useAppSelector(state => state.attendanceEntry.entities);
  const loading = useAppSelector(state => state.attendanceEntry.loading);
  const totalItems = useAppSelector(state => state.attendanceEntry.totalItems);
  let totalTime = 0;

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
      <h2 id="attendance-entry-heading" data-cy="AttendanceEntryHeading">
        Attendance Entries
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/attendance-entry/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Attendance Entry
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {attendanceEntryList && attendanceEntryList.length > 0 ? (
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
                <th className="hand" onClick={sort('yearMonth')}>
                  Year Month <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attStart')}>
                  Att Start <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attEnd')}>
                  Att End <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attEnd')}>
                  Time (Hrs.) <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('comment')}>
                  Comment <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {attendanceEntryList.map((attendanceEntry, i) => {
                let diffInHr = 0;
                if (attendanceEntry.attEnd) {
                  diffInHr = diffInHour(attendanceEntry.attStart, attendanceEntry.attEnd);
                  totalTime = totalTime + diffInHr;
                }

                return (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/attendance-entry/${attendanceEntry.id}`} color="link" size="sm">
                        {attendanceEntry.id}
                      </Button>
                    </td>
                    <td>{attendanceEntry.createdBy}</td>
                    <td>
                      {attendanceEntry.createdDate ? (
                        <TextFormat type="date" value={attendanceEntry.createdDate} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{attendanceEntry.lastModifiedBy}</td>
                    <td>
                      {attendanceEntry.lastModifiedDate ? (
                        <TextFormat type="date" value={attendanceEntry.lastModifiedDate} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{attendanceEntry.yearMonth}</td>
                    <td>
                      {attendanceEntry.attStart ? (
                        <TextFormat type="date" value={attendanceEntry.attStart} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {attendanceEntry.attEnd ? <TextFormat type="date" value={attendanceEntry.attEnd} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {attendanceEntry.attEnd ? (
                        <TextFormat type="number" value={diffInHr} format={APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT} />
                      ) : null}
                    </td>
                    <td>{attendanceEntry.comment}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/attendance-entry/${attendanceEntry.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/attendance-entry/${attendanceEntry.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/attendance-entry/${attendanceEntry.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                );
              })}
            </tbody>
            <tfoot>
              <tr>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th>
                  {totalTime ? <TextFormat type="number" value={totalTime} format={APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT} /> : null}
                </th>
                <th></th>
                <th />
              </tr>
            </tfoot>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Attendance Entries found</div>
        )}
      </div>
      {totalItems ? (
        <div className={attendanceEntryList && attendanceEntryList.length > 0 ? '' : 'd-none'}>
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

export default AttendanceEntry;
