import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './flora-employee.reducer';

export const FloraEmployeeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const floraEmployeeEntity = useAppSelector(state => state.floraEmployee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="floraEmployeeDetailsHeading">Flora Employee</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{floraEmployeeEntity.id}</dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{floraEmployeeEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {floraEmployeeEntity.createdDate ? (
              <TextFormat value={floraEmployeeEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{floraEmployeeEntity.lastModifiedBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {floraEmployeeEntity.lastModifiedDate ? (
              <TextFormat value={floraEmployeeEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="attStartTime1">Att Start Time 1</span>
          </dt>
          <dd>
            {floraEmployeeEntity.attStartTime1 ? (
              <TextFormat value={floraEmployeeEntity.attStartTime1} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="attEndTime1">Att End Time 1</span>
          </dt>
          <dd>
            {floraEmployeeEntity.attEndTime1 ? (
              <TextFormat value={floraEmployeeEntity.attEndTime1} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="attStartTime2">Att Start Time 2</span>
          </dt>
          <dd>
            {floraEmployeeEntity.attStartTime2 ? (
              <TextFormat value={floraEmployeeEntity.attStartTime2} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Internal User</dt>
          <dd>{floraEmployeeEntity.internalUser ? floraEmployeeEntity.internalUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/flora-employee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/flora-employee/${floraEmployeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FloraEmployeeDetail;
