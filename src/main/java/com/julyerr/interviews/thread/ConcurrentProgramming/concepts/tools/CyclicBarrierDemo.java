package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;


    private int computeValue(int x, int y) {
        return 0;
    }

    public CyclicBarrierDemo(final Board mainBoard) {
        this.mainBoard = mainBoard;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                mainBoard.commitNewValues();
            }
        });
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
        }
    }

    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            while (!board.hasConverged()) {
                for (int i = 0; i < board.getMaxX(); i++) {
                    for (int j = 0; j < board.getMaxY(); j++) {
                        board.setNewValue(i, j, computeValue(i, j));
                    }
                }
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                return;
            } catch (BrokenBarrierException e) {
                return;
            }
        }
    }


    private final class Board {
        private int[][] board;

        public Board(int[][] board) {
            this.board = board;
        }

        private void commitNewValues() {

        }

        private boolean hasConverged() {
            return false;
        }

        private Board getSubBoard(int count, int i) {
            return new Board(new int[][]{});
        }

        private void setNewValue(int x, int y, int newVal) {
            board[x][y] = newVal;
        }

        private int getMaxX() {
            return Integer.MAX_VALUE;
        }

        private int getMaxY() {
            return Integer.MAX_VALUE;
        }

    }
}
