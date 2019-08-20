import React, { useRef, useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Dialog from 'react-bootstrap-dialog'
import clearErrorMessage from '../redux/actions/clearErrorMessage';

const ErrorAlert = () => {
  const dialogRef = useRef(null);
  const { msg: errorMessage, counter: errorCounter } = useSelector((state => state.errorMessage));
  const dispatch = useDispatch();

  const [lastQuery, setLastQuery] = useState({});

  useEffect(() => {
    const query = {errorMessage, errorCounter};
    if (lastQuery != query && errorMessage !== "") {
      dialogRef.current.show({
        title: 'Błąd',
        body: errorMessage,
        actions: [Dialog.OKAction()],
        bsSize: 'large',
        onHide: () => {}
      });
      setLastQuery(query);
    }
  }, [errorMessage, errorCounter]);

  return (
    <Dialog ref={(component) => { dialogRef.current = component }} />
  );

};

export default ErrorAlert;
