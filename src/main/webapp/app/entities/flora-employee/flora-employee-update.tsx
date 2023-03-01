import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IFloraEmployee } from 'app/shared/model/flora-employee.model';
import { getEntity, updateEntity, createEntity, reset } from './flora-employee.reducer';

export const FloraEmployeeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const floraEmployeeEntity = useAppSelector(state => state.floraEmployee.entity);
  const loading = useAppSelector(state => state.floraEmployee.loading);
  const updating = useAppSelector(state => state.floraEmployee.updating);
  const updateSuccess = useAppSelector(state => state.floraEmployee.updateSuccess);

  const handleClose = () => {
    navigate('/flora-employee' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);
    values.attStartTime1 = convertDateTimeToServer(values.attStartTime1);
    values.attEndTime1 = convertDateTimeToServer(values.attEndTime1);
    values.attStartTime2 = convertDateTimeToServer(values.attStartTime2);

    const entity = {
      ...floraEmployeeEntity,
      ...values,
      internalUser: users.find(it => it.id.toString() === values.internalUser.toString()),
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
          attStartTime1: displayDefaultDateTime(),
          attEndTime1: displayDefaultDateTime(),
          attStartTime2: displayDefaultDateTime(),
        }
      : {
          ...floraEmployeeEntity,
          createdDate: convertDateTimeFromServer(floraEmployeeEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(floraEmployeeEntity.lastModifiedDate),
          attStartTime1: convertDateTimeFromServer(floraEmployeeEntity.attStartTime1),
          attEndTime1: convertDateTimeFromServer(floraEmployeeEntity.attEndTime1),
          attStartTime2: convertDateTimeFromServer(floraEmployeeEntity.attStartTime2),
          internalUser: floraEmployeeEntity?.internalUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="floraApp.floraEmployee.home.createOrEditLabel" data-cy="FloraEmployeeCreateUpdateHeading">
            Create or edit a Flora Employee
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
                <ValidatedField name="id" required readOnly id="flora-employee-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Created By"
                id="flora-employee-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
                validate={{
                  maxLength: { value: 50, message: 'This field cannot be longer than 50 characters.' },
                }}
              />
              <ValidatedField
                label="Created Date"
                id="flora-employee-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Last Modified By"
                id="flora-employee-lastModifiedBy"
                name="lastModifiedBy"
                data-cy="lastModifiedBy"
                type="text"
                validate={{
                  maxLength: { value: 50, message: 'This field cannot be longer than 50 characters.' },
                }}
              />
              <ValidatedField
                label="Last Modified Date"
                id="flora-employee-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Att Start Time 1"
                id="flora-employee-attStartTime1"
                name="attStartTime1"
                data-cy="attStartTime1"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Att End Time 1"
                id="flora-employee-attEndTime1"
                name="attEndTime1"
                data-cy="attEndTime1"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Att Start Time 2"
                id="flora-employee-attStartTime2"
                name="attStartTime2"
                data-cy="attStartTime2"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="flora-employee-internalUser"
                name="internalUser"
                data-cy="internalUser"
                label="Internal User"
                type="select"
                required
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/flora-employee" replace color="info">
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

export default FloraEmployeeUpdate;
