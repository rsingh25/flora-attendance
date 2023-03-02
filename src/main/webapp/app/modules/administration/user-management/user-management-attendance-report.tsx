import axios from 'axios';
import fileDownload from 'js-file-download';
import React, { useState, useEffect } from 'react';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Row, Col, Button } from 'reactstrap';
import { toast } from 'react-toastify';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { savePassword, reset } from '../../account/password/password.reducer';

export const AttendanceReport = () => {
  const [yearMon, setYearMon] = useState('');

  const { login } = useParams<'login'>();

  const handleValidSubmit = () => {
    handleDownload(`api/attendance-entries/download?createdBy.equals=${login}&yearMonth.equals=${yearMon}`, `${login}-${yearMon}.csv`);
  };

  const handleDownload = (url, filename) => {
    axios
      .get(url, {
        responseType: 'blob',
      })
      .then(res => {
        fileDownload(res.data, filename);
      });
  };

  const updatesetYearMon = event => setYearMon(event.target.value);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="report-title">
            Report for [<strong>{login}</strong>]
          </h2>
          <ValidatedForm id="report-form" onSubmit={handleValidSubmit}>
            <ValidatedField
              name="yearMon"
              label="Year And Month"
              placeholder="2022-01"
              type="text"
              validate={{
                required: { value: true, message: 'Year and Month is required.' },
                minLength: { value: 7, message: 'At least 7 characters.' },
                maxLength: { value: 7, message: 'At max 7 character.' },
                pattern: { value: /^202[3-9]-[0-1][0-9]$/, message: 'Invalid year month. is it in past?' },
              }}
              onChange={updatesetYearMon}
              data-cy="yearMon"
            />
            <Button tag={Link} to="/admin/user-management" replace color="info">
              <FontAwesomeIcon icon="arrow-left" />
              &nbsp;
              <span className="d-none d-md-inline">Back</span>
            </Button>
            &nbsp;
            <Button color="primary" type="submit">
              <FontAwesomeIcon icon="save" />
              Download Report
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default AttendanceReport;
