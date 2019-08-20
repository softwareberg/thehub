import { SET_ERROR_MESSAGE } from 'redux/actionsTypes';

export const setErrorMessage = (errorMessage = "Wystąpił błąd wewnętrzny aplikacji") => ({
  type: SET_ERROR_MESSAGE,
  errorMessage
});
export default setErrorMessage;
