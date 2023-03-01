import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attendance-entry.reducer';

export const AttendanceEntryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attendanceEntryEntity = useAppSelector(state => state.attendanceEntry.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attendanceEntryDetailsHeading">Attendance Entry</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{attendanceEntryEntity.id}</dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{attendanceEntryEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {attendanceEntryEntity.createdDate ? (
              <TextFormat value={attendanceEntryEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{attendanceEntryEntity.lastModifiedBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {attendanceEntryEntity.lastModifiedDate ? (
              <TextFormat value={attendanceEntryEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="yearMonth">Year Month</span>
          </dt>
          <dd>{attendanceEntryEntity.yearMonth}</dd>
          <dt>
            <span id="attStart">Att Start</span>
          </dt>
          <dd>
            {attendanceEntryEntity.attStart ? (
              <TextFormat value={attendanceEntryEntity.attStart} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="attEnd">Att End</span>
          </dt>
          <dd>
            {attendanceEntryEntity.attEnd ? <TextFormat value={attendanceEntryEntity.attEnd} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>{attendanceEntryEntity.comment}</dd>
        </dl>
        <Button tag={Link} to="/attendance-entry" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attendance-entry/${attendanceEntryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttendanceEntryDetail;
