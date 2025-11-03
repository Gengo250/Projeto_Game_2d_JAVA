package ai;

import java.util.ArrayList;
import main.GamePanel;

public class PathFind {
  GamePanel gp;
  Node[][] node;
  ArrayList<Node> openList = new ArrayList<>();
  public ArrayList<Node> pathList = new ArrayList<>();
  Node startNode, goalNode, currentNode;
  boolean goalReached = false;
  int step = 0;

  public PathFind(GamePanel gp) {
    this.gp = gp;
  }
  public void instantiateNodes(){
    node = new Node[gp.maxWorldCol][gp.maxWorldRow];
    int col = 0;
    int row = 0;
    while(col < gp.maxWorldCol && row < gp.maxWorldRow){
      node[col][row] = new Node(col, row);
      col++;
      if(col == gp.maxWorldCol){
        col = 0;
        row++;
      }
    }
  }
  public void resetNodes(){
    int col = 0;
    int row = 0;
    while(col < gp.maxWorldCol && row < gp.maxWorldRow){
      // Reset open, checked and solid state
      node[col][row].open = false;
      node[col][row].checked = false;
      node[col][row].solid = false;
      col++;
      if(col == gp.maxWorldCol){
        col = 0;
        row++;
      }
    }
    // Reset other settings
    openList.clear();
    pathList.clear();
    goalReached = false;
    step = 0;
  }
  public void setNode(int starCol, int startRow, int goalCol, int goalRow){
    resetNodes();
    // Set start and goal node
    startNode = node[starCol][startRow];
    currentNode = startNode;
    goalNode = node[goalCol][goalRow];
    openList.add(currentNode);

    int col = 0;
    int row = 0;
    while(col < gp.maxWorldCol && row < gp.maxWorldRow){
      // Set solid nodes
      // check tiles 
      int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
      if(gp.tileM.tile[tileNum].collision == true){
        node[col][row].solid = true;
      }
      // check interactive tiles
      for(int i = 0; i < gp.iTile[1].length; i++){
        if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i] .destructible == true){
          int itCol = gp.iTile[gp.currentMap][i].worldX / gp.tileSize;
          int itRow = gp.iTile[gp.currentMap][i].worldY / gp.tileSize;
          node[itCol][itRow].solid = true;
        }
      }
      // set cost
      getCost(node[col][row]);
      col++;
      if(col == gp.maxWorldCol){
        col = 0;
        row++;
      }
    }
  } 
  public void getCost(Node node){
    // G cost
    int xDistance = Math.abs(node.col - startNode.col);
    int yDistance = Math.abs(node.row - startNode.row);
    node.gCost = xDistance + yDistance;
    // H cost
    xDistance = Math.abs(node.col - goalNode.col);
    yDistance = Math.abs(node.row - goalNode.row);
    node.hCost = xDistance + yDistance;
    // F cost
    node.fCost = node.gCost + node.hCost;
  }
  public boolean search(){
    while(goalReached == false && step < 500){
      int col = currentNode.col;
      int row = currentNode.row;

      // Check the current node
      currentNode.checked = true;
      openList.remove(currentNode);

      // Check the neighboring nodes (up, down, left, right)
      // Up
      if(row - 1 >= 0){
        checkNode(node[col][row - 1]);
      }
      // Down
      if(row + 1 < gp.maxWorldRow){
        checkNode(node[col][row + 1]);
      }
      // Left
      if(col - 1 >= 0){
        checkNode(node[col - 1][row]);
      }
      // Right
      if(col + 1 < gp.maxWorldCol){
        checkNode(node[col + 1][row]);
      }
      // Find the node with the lowest fCost in the open list
      int bestNodeIndex = 0;
      int bestNodefCost = 999;
      for(int i = 0; i < openList.size(); i++){
        //check if this node's fCost is better
        if(openList.get(i).fCost < bestNodefCost){
          bestNodeIndex = i;
          bestNodefCost = openList.get(i).fCost;
        }
        //if F cost is equal, check G cost
        else if(openList.get(i).fCost == bestNodefCost){
          //check G cost
          if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
            bestNodeIndex = i;
          }
        }
      }
      // If there is node in the openList, end the loop
      if(openList.size() == 0){
        break;
      }
      // After the loop, openList[bestNodeIndex] is the new step (= currentNode)
      currentNode = openList.get(bestNodeIndex);
      if(currentNode == goalNode){
        goalReached = true;
        trackThePath();
      }
      step++;
    }
    return goalReached;
  }
  public void checkNode(Node node){
    if(node.open == false && node.checked == false && node.solid == false){
      node.open = true;
      node.parent = currentNode;
      openList.add(node);
    }
  }
  public void trackThePath(){
    Node current = goalNode;
    while(current != startNode){
      pathList.add(0, current);
      current = current.parent;
    }
  }
}
