package com.mygdx.hanto.util;

/**
 * This class is a data structure that records the move by a
 * player in a Hanto game. It is used for tournament play with AI players.
 * 
 */
public class HantoMoveRecord
{
	private final HantoPieceType piece;
	private final HantoCoordinate from;
	private final HantoCoordinate to;
	private HantoPlayerColor color = null;
	
	/**
	 * Constructor
	 * @param piece the Hanto piece type that moved
	 * @param from the source hex
	 * @param to the source hex
	 */
	public HantoMoveRecord(HantoPieceType piece, HantoCoordinate from, HantoCoordinate to)
	{
		this.piece = piece;
		this.from = from;
		this.to = to;
	}
	
	/**
	 * Constructor
	 * @param piece the Hanto piece type that moved
	 * @param from the source hex
	 * @param to the source hex
	 */
	public HantoMoveRecord(HantoPieceType piece, HantoCoordinate from, HantoCoordinate to, HantoPlayerColor color)
	{
		this(piece, from, to);
		this.color = color;
	}

	/**
	 * @return the piece
	 */
	public HantoPieceType getPiece()
	{
		return piece;
	}

	/**
	 * @return the from
	 */
	public HantoCoordinate getFrom()
	{
		return from;
	}

	/**
	 * @return the to
	 */
	public HantoCoordinate getTo()
	{
		return to;
	}
	
	public HantoPlayerColor getColor(){
		return color;
	}
	
	public void setColor(HantoPlayerColor color){
		this.color = color;
	}
}
