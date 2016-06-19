package ai;

import general.Item;
import items.Items;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import unit.Unit;
import blocks.Block;

public class PathFinding {
	
	public static List<Container> findPath(Item move, Block[][] blocks, List<Items> items, List<Unit> units, int[] end) {
		List<Container> moves = new ArrayList<Container>();
		int endX = end[0] / Block.BLOCK_SIZE;
		int endY = end[1] / Block.BLOCK_SIZE;
		int startX = (int) move.getPositionX() / Block.BLOCK_SIZE;
		int startY = (int) move.getPositionY() / Block.BLOCK_SIZE;
		if (startX == endX && startY == endY) {
			trim(moves, move, startX, startY);
			return moves;
		}
		Deque<BlockHelp> list = new LinkedList<BlockHelp>();
		
		BlockHelp[][] blHlp = new BlockHelp[blocks.length][blocks[0].length];
		for (int x = 0; x < blocks.length; x++) {
			for (int y = 0; y < blocks[0].length; y++) {
				blHlp[x][y] = new BlockHelp(blocks[x][y], x, y);
			}
		}
		blHlp[startX][startY].predecessor = blHlp[startX][startY];
		list.addLast(blHlp[startX][startY]);
		BlockHelp pom = list.removeFirst();
		while (pom.block != blocks[endX][endY]) {
			try {
				if (tryMove(move, blHlp, pom, -1, 0)) {
					list.addLast(blHlp[pom.x - 1][pom.y]);
					blHlp[pom.x - 1][pom.y].predecessor = pom;
				}
			} catch (Exception e) {}
			try {
				if (tryMove(move, blHlp, pom, 1, 0)) {
					list.addLast(blHlp[pom.x + 1][pom.y]);
					blHlp[pom.x + 1][pom.y].predecessor = pom;
				}
			} catch (Exception e) {}
			try {
				if (tryMove(move, blHlp, pom, 0, -1)) {
					list.addLast(blHlp[pom.x][pom.y - 1]);
					blHlp[pom.x][pom.y - 1].predecessor = pom;
				}
			} catch (Exception e) {}
			try {
				if (tryMove(move, blHlp, pom, 0, 1)) {
					list.addLast(blHlp[pom.x][pom.y + 1]);
					blHlp[pom.x][pom.y + 1].predecessor = pom;
				}
			} catch (Exception e) {}
			if (list.isEmpty()) {
				trim(moves, move, startX, startY);
				return moves;
			}
			else pom = list.removeFirst();
		}
		
		while (pom.block != blocks[startX][startY]) {
			int x = pom.x;
			int y = pom.y;
			pom = pom.predecessor;
			if (x < pom.x && y == pom.y) moves.add(new Container(EMoves.LEFT, Block.BLOCK_SIZE));
			else if (x > pom.x && y == pom.y) moves.add(new Container(EMoves.RIGHT, Block.BLOCK_SIZE));
			else if (x == pom.x && y < pom.y) moves.add(new Container(EMoves.UP, Block.BLOCK_SIZE));
			else if (x == pom.x && y > pom.y) moves.add(new Container(EMoves.DOWN, Block.BLOCK_SIZE));
		}
		
		trim(moves, move, startX, startY);
		return moves;
	}
	
	private static boolean tryMove(Item move, BlockHelp[][] blHlp, BlockHelp pom, int x, int y) { 
		if (blHlp[pom.x + x][pom.y + y].predecessor != null) return false;
		
		int sizeX = (int) ((move.getSizeX() - 0.01) / Block.BLOCK_SIZE);
		int sizeY = (int) ((move.getSizeY() - 0.01) / Block.BLOCK_SIZE);
		
		boolean possible = true;
		for (int i = 0; i <= sizeX; i++) {
			for (int j = 0; j <= sizeY; j++) {
				if (blHlp[pom.x + x + i][pom.y + y + j].block.isWall()) {
					possible = false;
					break;
				}
			}
			if (!possible) break;
		}
		
		return possible;
	}
	
	private static void trim(List<Container> moves, Item move, int startX, int startY) {
		double difX = move.getPositionX() - startX * Block.BLOCK_SIZE;
		double difY = move.getPositionY() - startY * Block.BLOCK_SIZE;
		if (difX != 0.0) moves.add(new Container(EMoves.LEFT, difX));
		if (difY != 0.0) moves.add(new Container(EMoves.UP, difY));
	}
	
	public static double distance(Unit alpha, Unit beta) {
		return Math.sqrt(Math.pow(alpha.getPositionX() - beta.getPositionX(), 2) + Math.pow(alpha.getPositionY() - beta.getPositionY(), 2));
	}
}

class BlockHelp {
	Block block;
	BlockHelp predecessor;
	int x;
	int y;
	
	public BlockHelp(Block block, int x, int y) {
		this.block = block;
		predecessor = null;
		this.x = x;
		this.y = y;
	}
}
