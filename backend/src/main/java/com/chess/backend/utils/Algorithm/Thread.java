package com.chess.backend.utils.Algorithm;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Thread {
    public static class MyRunnable implements Runnable {
        private Node node;
        public MyRunnable(Node node) {
            this.node = node;
        }
        public void run() {
            Tree.build_tree(node);
            Tree.set_tree_score(node);
        }
    }
    public static void batch_run(List<Node> children_board) {
        if(children_board.size() <= Data.pool_size) {
            for(Node i : children_board) {
                batch_run(i.children_board);
                i.set_score();
            }
            return ;
        }
        ExecutorService pool = Executors.newFixedThreadPool(children_board.size());
        for(Node i : children_board) {
            pool.submit(new MyRunnable(i));
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        }
        catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
