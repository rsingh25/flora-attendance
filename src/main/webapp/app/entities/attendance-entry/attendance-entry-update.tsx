import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAttendanceEntry } from 'app/shared/model/attendance-entry.model';
import { getEntity, updateEntity, createEntity, reset } from './attendance-entry.reducer';

export const AttendanceEntryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const attendanceEntryEntity = useAppSelector(state => state.attendanceEntry.entity);
  const loading = useAppSelector(state => state.attendanceEntry.loading);
  const updating = useAppSelector(state => state.attendanceEntry.updating);
  const updateSuccess = useAppSelector(state => state.attendanceEntry.updateSuccess);

  const handleClose = () => {
    navigate('/attendance-entry' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);
    values.attStart = convertDateTimeToServer(values.attStart);
    values.attEnd = convertDateTimeToServer(values.attEnd);

    const entity = {
      ...attendanceEntryEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdDate: displayDefaultDateTime(),
          lastModifiedDate: displayDefaultDateTime(),
          attStart: displayDefaultDateTime(),
          attEnd: displayDefaultDateTime(),
        }
      : {
          ...attendanceEntryEntity,
          createdDate: convertDateTimeFromServer(attendanceEntryEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(attendanceEntryEntity.lastModifiedDate),
          attStart: convertDateTimeFromServer(attendanceEntryEntity.attStart),
          attEnd: convertDateTimeFromServer(attendanceEntryEntity.attEnd),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="floraApp.attendanceEntry.home.createOrEditLabel" data-cy="AttendanceEntryCreateUpdateHeading">
            Create or edit a Attendance Entry
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="attendance-entry-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Created By"
                id="attendance-entry-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
                validate={{
                  maxLength: { value: 50, message: 'This field cannot be longer than 50 characters.' },
                }}
              />
              <ValidatedField
                label="Created Date"
                id="attendance-entry-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Last Modified By"
                id="attendance-entry-lastModifiedBy"
                name="lastModifiedBy"
                data-cy="lastModifiedBy"
                type="text"
                validate={{
                  maxLength: { value: 50, message: 'This field cannot be longer than 50 characters.' },
                }}
              />
              <ValidatedField
                label="Last Modified Date"
                id="attendance-entry-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Year Month" id="attendance-entry-yearMonth" name="yearMonth" data-cy="yearMonth" type="text" />
              <ValidatedField
                label="Att Start"
                id="attendance-entry-attStart"
                name="attStart"
                data-cy="attStart"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Att End"
                id="attendance-entry-attEnd"
                name="attEnd"
                data-cy="attEnd"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Comment" id="attendance-entry-comment" name="comment" data-cy="comment" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/attendance-entry" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AttendanceEntryUpdate;
