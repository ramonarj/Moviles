using System.Collections;
using System.Collections.Generic;
using System;

namespace Utils
{
    public enum DirectionEnum { Up, Down, Left, Right, None };


    public struct Direction
    {
        public DirectionEnum dir;

        public Direction(DirectionEnum newDir)
        {
            dir = newDir;
        }

        public void setDirection(DirectionEnum newDir)
        {
            dir = newDir;
        }

        public Direction inverse()
        {
            if (dir == DirectionEnum.Right)
                return new Direction(DirectionEnum.Left);
            else if (dir == DirectionEnum.Left)
                return new Direction(DirectionEnum.Right);
            else if (dir == DirectionEnum.Up)
                return new Direction(DirectionEnum.Down);
            else if (dir == DirectionEnum.Down)
                return new Direction(DirectionEnum.Up);
            else
                return new Direction(DirectionEnum.None);
        }
    }
}
