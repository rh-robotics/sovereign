package org.ironlions.sovereign.geometry

/**
 * A regular grid we can overlay on top of the FTC grid.
 *
 * @param tilesPerSide The number of grid elements (tiles) on one side of the grid.
 * @param tileSideLength The side length of one of the tiles.
 */
class Grid(val tilesPerSide: Int, val tileSideLength: Measurement) {
    /** Get the origin of the grid. */
    fun origin(): Point = Point(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0))

    /**
     * Converts a coordinate into a measurement.
     * TODO: Figure out why we need the times 2 here.
     *
     * @param a The coordinate to convert.
     */
    fun realA(a: Int): Measurement = tileSideLength * a * 2

    /**
     * Converts a coordinate pair into a point.
     *
     * @param x The x coordinate to convert.
     * @param y The y coordinate to convert.
     * */
    fun toFake(x: Int, y: Int, z: Measurement = Measurement.Millimeters(0.0)): Point =
        Point(realA(x), realA(y), z)

    /** Re-express the space that the grid represents in a new object. Clones, without copying
     * the underlying grid data. */
    fun reExpress(): Grid = Grid(tilesPerSide, tileSideLength)

    /** Re-express the space that the grid represents in a new object, with a new number of tiles.
     * Clones, without copying the underlying grid data. */
    fun reExpress(newTilesPerSide: Int): Grid =
        Grid(newTilesPerSide, tileSideLength * (newTilesPerSide.toDouble() / tilesPerSide))
}