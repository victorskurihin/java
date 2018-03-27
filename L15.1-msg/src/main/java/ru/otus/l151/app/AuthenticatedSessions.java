package ru.otus.l151.app;

import javax.websocket.Session;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class AuthenticatedSessions {
    private final Map<String, Session> mapAuths = new ConcurrentHashMap<>();
    private final Map<String, String> sessionToAuth = new ConcurrentHashMap<>();

    public Session put(String auth, Session session) {
        sessionToAuth.put(session.getId(), auth);
        return mapAuths.put(auth, session);
    }

    public Session get(String auth) {
        return mapAuths.get(auth);
    }

    public Session getOrDefault(String auth, Session session) {
        return mapAuths.getOrDefault(auth, session);
    }

    public String getAuth(String sessionId) {
        return sessionToAuth.get(sessionId);
    }

    public String getAuthOrDefault(String sessionId, String auth) {
        return sessionToAuth.getOrDefault(sessionId, auth);
    }

    public boolean containsKey(String auth) {
        return mapAuths.containsKey(auth);
    }

    public boolean containsAuth(String auth) {
        return containsKey(auth);
    }

    public boolean containsSessionId(String sessionId) {
        return sessionToAuth.containsKey(sessionId);
    }

    public Session remove(String auth) {
        Session session = mapAuths.remove(auth);
        if (null != session) {
            sessionToAuth.remove(session.getId());
        }
        return session;
    }

    public String removeBySessionId(String sessionId) {
        String user = sessionToAuth.get(sessionId);
        if (null != user) {
            mapAuths.remove(user);
        }
        return sessionToAuth.remove(sessionId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthenticatedSessions)) return false;
        AuthenticatedSessions that = (AuthenticatedSessions) o;
        return Objects.equals(mapAuths, that.mapAuths) &&
            Objects.equals(sessionToAuth, that.sessionToAuth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mapAuths, sessionToAuth);
    }

    @Override
    public String toString() {
        return "AuthenticatedSessions{" +
            "mapAuths=" + mapAuths +
            ", sessionToAuth=" + sessionToAuth +
            '}';
    }
}
