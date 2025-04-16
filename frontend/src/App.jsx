 import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import ProfilePage from './pages/ProfilePage';
import MapPage from './pages/MapPage';
import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import './assets/css/style.css';

function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/match/:matchId" element={<MapPage />} />
      </Routes>
    </>
  );
}

export default App;
