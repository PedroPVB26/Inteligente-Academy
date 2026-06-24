import { Navigate } from 'react-router-dom';
import { isAdmin } from '../../services/authService';

export default function ProtectedRoute({ children }) {
  return isAdmin() ? children : <Navigate to="/" replace />;
}
