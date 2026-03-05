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
    private int assignedUnitID;
    private int ticksRemaining;
    
    public Incident(int id, IncidentType type, int severity, int x, int y) {
        this.incidentID = id;
        this.type = type;
        this.severity = severity;
        this.x = x;
        this.y = y;
        if (type == IncidentType.MEDICAL){this.ticksRemaining = 2;}
        else if (type == IncidentType.CRIME){this.ticksRemaining = 3;}
        else if (type == IncidentType.FIRE){this.ticksRemaining = 4;}
        this.assignedUnitID = -1; // -1 means no assigned unit
        this.status = IncidentStatus.REPORTED;
    }

    public int getIncidentId() {return incidentID;}
    public IncidentType getIncidentType() {return type;}
    public int getX() {return x;}
    public int getY() {return y;}
    public void setAssignedUnitID(int UnitID){assignedUnitID = UnitID;}
    public int getAssignedUnitID(){return assignedUnitID;}

    public int getSeverity() {return severity;}
    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public IncidentStatus getStatus() {return status;}
    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

    public void subtractTick(){
        ticksRemaining--;
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

    private CityMap map;
    private Station[] stations = new Station[MAX_STATIONS];
    private Unit[] units = new Unit[MAX_UNITS];
    private Incident[] incidents = new Incident[MAX_INCIDENTS];
    private int stationCount = 0;
    private int unitCount = 0;
    private int incidentCount = 0;
    private int currentTick = 0;
    private int stationIDCounter = 1;
    private int unitIDCounter = 1;
    private int incidentIDCounter = 1;





    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // TODO: implement
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException("Width and height must both be greater than 0");
        }

        map = new CityMap(width, height);
        stations = new Station[MAX_STATIONS];
        units = new Unit[MAX_UNITS];
        incidents = new Incident[MAX_INCIDENTS];

        stationCount = 0;
        unitCount = 0;
        incidentCount = 0;

        currentTik = 0;
    }

    @Override
    public int[] getGridSize() {
        // TODO: implement
        return new int[] {map.getWidth(), map.getHeight() };
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        if (!isLocationValid(x, y)) {
            throw new InvalidLocationException("Invalid grid location");
        }

        map.setBlocked(x, y, true);
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        if (!isLocationValid(x, y)) {
            throw new InvalidLocationException("Invalid grid location");
        }

        map.setBlocked(x, y, false);
    }

    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        // TODO: implement
        if (name == null) {
            throw new InvalidNameException("Name cannot be blank");   
        }

        if (!isLocationValid(x, y) || map.isBlocked(x, y)) {
            throw new InvalidLocationException("Station cannot be placed out of bounds or on an obstacle");
        }

        int stationId = stationCount + 1;

        Station station = new Station(stationId, name, x, y);
        stations[stationCount] = station;

        stationCount++;
        
        return stationId;
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].getStationId() == stationId) {
                if (stations[i].getStationUnitCount() > 0) {
                    throw new IllegalStateException("Station still has units");
                }

                // shift all other stations down an index in the array
                for (int j = i; j < stationCount - 1; j++) {
                    stations[j] = stations[j + 1];
                }

                stations[stationCount - 1] = null;
                stationCount--;
            }
        }

        throw new IDNotRecognisedException("No station with that stationID");
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        // TODO: implement
        Station station = findStation(stationId);

        if (station == null) {
            throw new IDNotRecognisedException("No station with that stationID");
        }

        if (maxUnits <=0 || maxUnits < station.getStationUnitCount()) {
            throw new InvalidCapacityException("Max units must be greater than 0 or current units in station");
        }

        station.setCapacity(maxUnits);
    }

    @Override
    public int[] getStationIds() {
        // TODO: implement
        int[] stationdIds = new int[stationCount];

        for (int i = 0; i < stationCount; i++) {
            stationdIds[i] = stations[i].getStationId();
        }

        return stationdIds;
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        if (type == null) {
            throw new InvalidUnitException("");
        }

        Station station = findStation(stationId);

        if (station == null) {
            throw new IDNotRecognisedException("No station with that stationID");
        }

        if (station.getStationUnitCount() >= station.getCapacity()) {
            throw new IllegalStateException("Station full");
        }

        if (unitCount == MAX_UNITS) {
            throw new IllegalStateException("Max units reached");
        }

        int unitID = unitCount + 1;
        int x = station.getX();
        int y = station.getY();

        Unit unit;

        switch (type) {
            case AMBULANCE:
                unit = new Ambulance(unitID, stationId, x, y);
                break;
            case FIRE_ENGINE:
                unit = new FireTruck(unitID, stationId, x, y);
                break;
            case POLICE_CAR:
                unit = new PoliceCar(unitID, stationId, x, y);
                break;
            default:
                throw new InvalidUnitException("");
        }

        units[unitCount] = unit;
        unitCount++;

        station.addUnit(unit);
        return unitID;
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        Unit unit = findUnit(unitId);

        if (unit == null) {
            throw new IDNotRecognisedException("No unit with that unitId");
        }

        if (unit.getStatus() == UnitStatus.AT_SCENE || unit.getStatus() == UnitStatus.EN_ROUTE) {
            throw new IllegalStateException("Unit is busy");
        }

        Station station = findStation(unit.getHomeStationId());
        station.removeUnit(unitId);

        // remove from global array aswell
        for (int i =0; i < unitCount; i++) {
            if (units[i].getUnitId() == unitId) {
                
                //shift every array item down
                for (int j = i ; j < unitCount - 1; j++) {
                    units[j] = units[j + 1];
                }

                units[unitCount-1] = null;
                unitCount--;
                break;
            }
        }
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        Unit unit = findUnit(unitId);
        Station newStation = findStation(newStationId);

        if (unit == null || newStation == null) {
            throw new IDNotRecognisedException("");
        }

        if (unit.getStatus() != UnitStatus.IDLE) {
            throw new IllegalStateException("Unit is not idle");
        }

        if (newStation.getStationUnitCount() >= newStation.getCapacity()) {
            throw new IllegalStateException("New station is full");
        }

        Station oldStation = findStation(unit.getHomeStationId());

        oldStation.removeUnit(unitId);
        newStation.addUnit(unit);
        unit.setHomeStationId(newStationId);
        unit.setLocation(newStation.getX(), newStation.getY());
    }

    public int manhattanDist(int x0, int x1, int y0, int y1){
        return Math.abs(x0 - x1) + Math.abs(y0-y1);
    }


    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        Unit unit = findUnit(unitId);
        if (outOfService){
            if (units[unitID].getStatus() == UnitStatus.OUT_OF_SERVICE){
                throw new IllegalStateException("Unit is already out of service")
            }
            else{
                units[unitID].setStatus(UnitStatus.IDLE);
            }
        }
        else{
            if (units[unitID].getStatus() == UnitStatus.IDLE){
                throw new IllegalStateException("Unit is already idle")
            }
            else{   
                units[unitID].setStatus(UnitStatus.OUT_OF_SERVICE);
            }
        }
    }

    @Override
    public int[] getUnitIds() {
        int[] ids = new int[unitCount];
        int idx = 0;
        for(int i = 0;i<MAX_UNITS;i++){
            if (units[i] != null)ids[idx++] = i;
        }
        return ids;
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        if (unitId > MAX_UNITS){
            throw new IDNotRecognisedException("Invalid unit ID");
        }
        Unit unit = findUnit(unitId);
        return String.format(
            "Unit ID: %\nType: %s\nHome Station ID: %d\nPosition: (%d, %d)\nStatus: %s",
            unit.getUnitId(),
            unit.getType(),
            unit.getHomeStationId(),
            unit.getX(),
            unit.getY(),
            unit.getStatus()
        );
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        if (type == null){
            throw new InvalidSeverityException("Incident has no type");
        }
        if (severity < 1 || severity > 5){
            throw new InvalidSeverityException("Severity not in range");
        }
        if (x >= map.getWidth() || x < 0 || y >= map.getHeight() || y < 0){
            throw new InvalidLocationException("Position not in bounds");
        }
        if (incidentCount == MAX_INCIDENTS){
            throw new InvalidLocationException("maximum number of incidents reached");
        }

        for(int i = 0;i<;i++){
            if (incidents[i] == null){
                incidents[i] = Incident(prevIncidentID++,type,severity,x,y);
                return i;
            }
        }
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        if (incidentId > MAX_INCIDENTS || incidentId < 0 || incidents[incidentId] == null){
            throw new IDNotRecognisedException("No incident with that ID");
        }
        if (incidents[incidentId].getStatus() == IncidentStatus.CANCELLED){
            throw new IllegalStateException("Incident already cancelled");
        }
        findIncident(incidentId).setStatus(IncidentStatus.CANCELLED);
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        if (incidentId > MAX_INCIDENTS || incidentId < 0 || incidents[incidentId] == null){
            throw new IDNotRecognisedException("No incident with that ID");
        }
        if (newSeverity > 5 || newSeverity < 0){
            throw new InvalidSeverityException("Severity not within range");
        }
        findIncident(incidentId).setSeverity(newSeverity);
    }

    @Override
    public int[] getIncidentIds() {
        int[] ids = new int[incidentCount];
        int idx = 0;
        for(int i = 0;i<MAX_INCIDENTS;i++){
            if (incidents[i] != null)ids[idx++] = i;
        }
        return ids;
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        if (incidentId > MAX_INCIDENTS || incidentId < 0 || incidents[incidentId] == null){
            throw new IDNotRecognisedException("No incident with that ID");
        }
        Unit unit = units[unitID];
        return String.format(
            "Incident ID: %\nType: %s\nSeverity: %d\nPosition: (%d, %d)\nStatus: %s",
            incidentId,
            incident.getIncidentType(),
            incident.getSeverity(),
            incident.getX(),
            incident.getY(),
            incident.getStatus()
        );
    }

    @Override
    public void dispatch() {
        int[] incidentIDs = getIncidentIds();
        int[] unitIDs = getUnitIds();
        for (int i = 0;i<incidentCount;i++){
            Incident incident = findIncident(incidentIDs[i]);
            if (incident.getStatus() != REPORTED){continue;}

            int bestUnitID = -1;
            int bestDist = map.getHeight() + map.getWidth();
            int incidentX = incident.getX();
            int incidentY = incident.getY();
            
            for (int j = 0;j<unitCount;j++){
                int unitID = unitIDs[j];
                Unit unit = findUnit(unitID)
                int unitX = units[j].getX();
                int unitY = units[j].getY();
                int manhattanDist = Math.abs(unitX-incidentX) + Math.abs(unitY-incidentY);
                
                if (manhattanDist < bestDist){
                    bestDist = manhattanDist;
                    bestUnitID = unitID;
                }
            }
            if (bestUnitID != -1){
                incidents[i].setStatus(UnitStatus.DISPATCHED);
                incidents.setAssignedUnitID(bestUnitID);
            }
        }
    }

    @Override
    public void tick() {
        currentTick++;
        int[] incidentIDs = getIncidentIds();
        for(int i = 0;i<incidentCount;i++){
            Incident incident = findIncident(incidentIDs[i])
            IncidentStatus status = incident.getStatus();
            if (status == IncidentStatus.DISPATCHED){
                int assignedUnitID = incident.getAssignedUnitId();
                Unit unit = findUnit(assignedUnitID);
                int unitX = unit.getX()
                int unitY = unit.getY()
                int incidentX = incident.getX();
                int incidentY = incident.getY();
                int baseDist = manhattanDist(unitX,incidentX,unitY,incidentY);

                if (baseDist == 0){
                    // assume a unit needs to be at a tile for 1 tick for progress to start
                    incident.setStatus(IncidentStatus.IN_PROGRESS);
                    continue;
                }

                if (unitX > 0 && map.isBlocked(unitX-1,unitY) == False){
                    // check the move West

                    // technically dont need to calculate manhattan distance for each move
                    // could just use the sign of unitX-incidentX 
                    if (manhattanDist(unitX-1,incidentX,unitY,incidentY) < baseDist){
                        unit.setLocation(unitX-1,unitY);
                    }
                }
                else if (unitY < map.getHeight()-1 && map.isBlocked(unitX,unitY+1) == False){
                    if (manhattandist(unitX,incidentX,unitY+1,incidentY) < baseDist){
                        unit.setLocation(unitX,unitY+1);
                    }
                }
                else if (unitX < map.getWidth()-1 && map.isBlocked(unitX+1,unitY) == False){
                    if (manhattanDist(unitX+1,incidentX,unitY,incidentY) < baseDist){
                        unit.setLocation(unitX+1,unitY);
                    }
                }
                else if (unitY > 0 && map.isBlocked(unitX,unitY-1) == False){
                    if (manhattanDist(unitX,incidentX,unitY-1,incidentY) < baseDist){
                        unit.setLocation(unitX,unitY-1);
                    }
                }
            }
            else if (status == IncidentStatus.IN_PROGRESS){
                incident.subtractTick();
                if (incident.getTicksRemaining() == 0){
                    incident.setStatus(IncidentStatus.RESOLVED);
                }
            }
        }
    }


    @Override
    public String getStatus() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
