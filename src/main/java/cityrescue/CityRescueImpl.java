package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */


class CityMap {

    private final int width;
    private final int height;
    private final boolean[][] blocked;

    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.blocked = new boolean[width][height]; //all cells initialised to false
    }

    public int getWidth() {return width;}
    public int getHeight() {return height;}

    public boolean isBlocked(int x, int y) {
        return blocked[x][y];
    }
}

class Station {

    private int stationId;
    private int stationCapacity;
    private UnitType[] unitTypes;
    private int x;
    private int y;

    public Station(int id, int x, int y) {
        this.stationId = id;
        this.x = x;
        this.y = y;
    }

    public int getStationId() {return stationId;}
    public int getX() {return x;}
    public int getY() {return y;}
    
    public int getCapacity() {return stationCapacity;}
    public void setCapacity(int capacity) {
        this.stationCapacity = capacity;
    }

    public UnitType[] getUnitTypes() {return unitTypes;}


}

class Incident {
    
    private int incidentID;
    private IncidentType type;
    private int severity;
    private IncidentStatus status;
    private int x;
    private int y;
    
    public Incident(int id, IncidentType type, int severity, int x, int y) {
        this.incidentID = id;
        this.type = type;
        this.severity = severity;
        this.x = x;
        this.y =y;
        this.status = IncidentStatus.REPORTED;
    }

    public int getIncidentId() {return incidentID;}
    public IncidentType getIncidentType() {return type;}
    public int getX() {return x;}
    public int getY() {return y;}

    public int getSeverity() {return severity;}
    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public IncidentStatus getStatus() {return status;}
    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

}

abstract class Unit {

    private int unitID;
    private UnitType type;
    private int homeStationId;
    private int x;
    private int y;
    private UnitStatus status = UnitStatus.IDLE;

    public Unit(int id, UnitType type, int homeStationId, int x, int y) {
        this.unitID = id;
        this.type = type;
        this.homeStationId = homeStationId;
        this.x = x;
        this.y = y;
    }

    public int getUnitId() {return unitID;}
    public UnitType getType() {return type;}
    public int getHomeStationId() {return homeStationId;}
    
    public int getX() {return x;}
    public int getY() {return y;}
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public UnitStatus getStatus() {return status;}
    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    public abstract boolean canHandle(IncidentType type);
}

class FireTruck extends Unit {
    
    public FireTruck(int id, int homeStationId, int x, int y) {
        super(id, UnitType.FIRE_ENGINE, homeStationId, x, y);
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.FIRE;
    }

}

class PoliceCar extends Unit {

    public PoliceCar(int id, int homeStationId, int x, int y) {
        super(id, UnitType.POLICE_CAR, homeStationId, x, y);
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.CRIME;
    }
}

class Ambulance extends Unit {

    public Ambulance(int id, int homeStationId, int x, int y) {
        super(id, UnitType.AMBULANCE, homeStationId, x, y);
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.MEDICAL;
    }
}


public class CityRescueImpl implements CityRescue {

    static final int MAX_STATIONS = 15;
    static final int MAX_UNITS = 40;
    static final int MAX_INCIDENTS = 180;





    // TODO: add fields (map, arrays for stations/units/incidents, counters, tick, etc.)


    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getGridSize() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getStationIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
