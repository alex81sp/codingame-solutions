/* https://www.codingame.com/ide/puzzle/shadows-of-the-knight-episode-1 */

import java.util.Objects;
import java.util.Scanner;
import java.util.function.BiFunction;

class Player {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        final int buildingWidth = in.nextInt();
        int buildingHeight = in.nextInt();
        int maxTurnsGame = in.nextInt();
        int posX = in.nextInt();
        int posY = in.nextInt();

        final Building building = new Building(buildingWidth, buildingHeight);
        final BombFinder bombFinder = new BombFinder(building, in);
        final Batman batman = new Batman(new Position(posX, posY));

        new Game(batman, maxTurnsGame, bombFinder).run();
    }

    static class Game {
        private final Batman batman;
        private final int maxTurnsGame;
        private final BombFinder bombFinder;

        public Game(Batman batman, int maxTurnsGame, BombFinder bombFinder) {
            this.batman = batman;
            this.maxTurnsGame = maxTurnsGame;
            this.bombFinder = bombFinder;
        }

        private void run() {
            int turn = 0;
            while (turn < maxTurnsGame) {
                batman.moveTo(bombFinder.bestNextPositionFrom(batman.position));
                batman.printCurrentPosition();
                turn++;
            }
        }
    }

    private static class BombFinder {
        private final Scanner in;
        private Search search;

        public BombFinder(Building building, Scanner in) {
            this.in = in;
            this.search = new Search(building);
        }

        public Position bestNextPositionFrom(Position xy) {
            this.search = fetchBombDirection().calculateLimits.apply(xy, search);
            return search.nextPosition();
        }

        private Direction fetchBombDirection() {
            return Direction.valueOf(in.next());
        }

        enum Direction {
            U((p, s) -> new Search(s.x1, s.x2, s.y1, p.y - 1)),
            UR((p, s) -> new Search(p.x + 1, s.x2, s.y1, p.y - 1)),
            R((p, s) -> new Search(p.x + 1, s.x2, s.y1, s.y2)),
            DR((p, s) -> new Search(p.x + 1, s.x2, p.y + 1, s.y2)),
            D((p, s) -> new Search(s.x1, s.x2, p.y + 1, s.y2)),
            DL((p, s) -> new Search(s.x1, p.x - 1, p.y + 1, s.y2)),
            L((p, s) -> new Search(s.x1, p.x - 1, s.y1, s.y2)),
            UL((p, s) -> new Search(s.x1, p.x - 1, s.y1, p.y - 1));

            private final BiFunction<Position, Search, Search> calculateLimits;

            Direction(BiFunction<Position, Search, Search> calculateLimits) {
                this.calculateLimits = calculateLimits;
            }
        }
    }

    private static class Search {
        int x1;
        int x2;
        int y1;
        int y2;

        public Search(Building building) {
            this.x1 = 0;
            this.x2 = building.width - 1;
            this.y1 = 0;
            this.y2 = building.height - 1;
        }

        public Search(int x1, int x2, int y1, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        public Position nextPosition() {
            return new Position(nextPositionOf(x1, x2), nextPositionOf(y1, y2));
        }

        private int nextPositionOf(int first, int last) {
            return first + (last - first) / 2;
        }
    }

    static class Batman {

        private Position position;

        public Batman(Position position) {
            Objects.requireNonNull(position);
            this.position = position;
        }

        public void moveTo(Position position) {
            Objects.requireNonNull(position);
            this.position = position;
        }

        public void printCurrentPosition() {
            System.out.println(position.toString());
        }

    }

    static class Position {
        private final int x;
        private final int y;

        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return x + " " + y;
        }
    }

    static class Building {

        private final int width;
        private final int height;

        public Building(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}