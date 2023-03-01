import React, { useState, useEffect } from 'react';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Row, Col, Button } from 'reactstrap';
import { toast } from 'react-toastify';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { savePassword, reset } from '../../account/password/password.reducer';

export const PasswordPage = () => {
  const [password, setPassword] = useState('');
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const { login } = useParams<'login'>();

  const handleValidSubmit = ({ newPassword }) => {
    dispatch(savePassword({ newPassword, login }));
  };

  const updatePassword = event => setPassword(event.target.value);

  const successMessage = useAppSelector(state => state.password.successMessage);
  const errorMessage = useAppSelector(state => state.password.errorMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    } else if (errorMessage) {
      toast.error(errorMessage);
    }
    dispatch(reset());
  }, [successMessage, errorMessage]);

  const handleClose = () => {
    navigate('/admin/user-management');
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="password-title">
            Password for [<strong>{login}</strong>]
          </h2>
          <ValidatedForm id="password-form" onSubmit={handleValidSubmit}>
            <ValidatedField
              name="newPassword"
              label="New password"
              placeholder="New password"
              type="password"
              validate={{
                required: { value: true, message: 'Your password is required.' },
                minLength: { value: 4, message: 'Your password is required to be at least 4 characters.' },
                maxLength: { value: 50, message: 'Your password cannot be longer than 50 characters.' },
              }}
              onChange={updatePassword}
              data-cy="newPassword"
            />
            <PasswordStrengthBar password={password} />
            <Button tag={Link} to="/admin/user-management" replace color="info">
              <FontAwesomeIcon icon="arrow-left" />
              &nbsp;
              <span className="d-none d-md-inline">Back</span>
            </Button>
            &nbsp;
            <Button color="primary" type="submit">
              <FontAwesomeIcon icon="save" />
              &nbsp; Save
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default PasswordPage;
