package ar.edu.davinci.dvdsPerezMartin.servicio;

public interface SeguridadInterfazServicio {
	boolean isAuthenticated();
    void autoLogin(String email, String password);
}

