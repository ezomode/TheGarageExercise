package com.wreck.garage.one;

public class ParkingSpace {

    private final int levelNumber;
    private final int spaceNumber;
//    private boolean isFree;

    ParkingSpace(int levelNumber, int spaceNumber) {
        this.levelNumber = levelNumber;
        this.spaceNumber = spaceNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getSpaceNumber() {
        return spaceNumber;
    }

//    public boolean isFree() {
//        return isFree;
//    }
//
//    public void setIsFree(boolean isFree) {
//        this.isFree = isFree;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParkingSpace that = (ParkingSpace) o;

        return levelNumber == that.levelNumber && spaceNumber == that.spaceNumber;
    }

    @Override
    public int hashCode() {
        int result = levelNumber;
        result = 31 * result + spaceNumber;

        return result;
    }

    @Override
    public String toString() {
        return "ParkingSpace{" +
                "spaceNumber=" + spaceNumber +
                ", levelNumber=" + levelNumber +
                '}';
    }
}
