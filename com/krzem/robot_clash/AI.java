package com.krzem.robot_clash;



import java.lang.Math;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;



public class AI extends Constants{
	public Main cls;
	public Engine e;
	public int t;
	private Map<int[],Double> _scl=new HashMap<int[],Double>();



	public AI(Main cls,Engine e,int t){
		this.cls=cls;
		this.e=e;
		this.t=t;
	}



	public void update(){
		if (this.e.t==this.t){
			List<int[]> pm=this._possible_moves(this.e.b,this.t);
			this._scl.clear();
			List<Board> pmb=new ArrayList<Board>();
			for (int[] mv:pm){
				pmb.add(this._clone_make_move(this.e.b,mv));
			}
			for (int i=0;i<pm.size();i++){
				this._minmax_root(pm.get(i),pmb.get(i));
			}
			int[] bm=null;
			double bms=-Double.MAX_VALUE;
			for (Map.Entry<int[],Double> e:this._scl.entrySet()){
				double sc=(double)e.getValue();
				if (sc>=bms){
					bms=sc;
					bm=e.getKey();
				}
			}
			this.e.b.move(this.e.b.b[bm[1]][bm[0]],bm[2],bm[3]);
			this.e.t=-this.e.t;
		}
	}



	private void _minmax_root(int[] mv,Board b){
		this._scl.put(mv,this._minmax(b,-Double.MAX_VALUE,Double.MAX_VALUE,AI_MINMAX_DEPTH,-this.t));
	}



	private double _minmax(Board b,double ao,double bo,int d,int cl){
		if (d==0||b.w!=0){
			return this._eval_board(b,d);
		}
		if (cl==1){
			List<int[]> pm=this._possible_moves(b,1);
			double bm=bo+0;
			for (int[] mv:pm){
				Board nb=this._clone_make_move(b,mv);
				bm=Math.min(bm,(nb.w==0?this._minmax(nb,ao,bo,d-1,-1):0)+this._eval_board(nb,d));
				bo=Math.min(bo,bm);
				if (bo<=ao){
					return bm;
				}
			}
			return bm;
		}
		else{
			List<int[]> pm=this._possible_moves(b,-1);
			double bm=ao+0;
			for(int[] mv:pm){
				Board nb=this._clone_make_move(b,mv);
				bm=Math.max(bm,(nb.w==0?this._minmax(nb,ao,bo,d-1,1):0)+this._eval_board(nb,d));
				ao=Math.max(ao,bm);
				if (bo<=ao){
					return bm;
				}
			}
			return bm;
		}
	}



	private Board _clone_b(Board b){
		return new Board(b);
	}



	private Board _clone_make_move(Board b,int[] mv){
		b=this._clone_b(b);
		b.move(b.b[mv[1]][mv[0]],mv[2],mv[3]);
		// System.out.println(b.w);
		if (b.w!=0){
			for (int j=0;j<4;j++){
				for (int i=0;i<3;i++){
					if (b.b[j][i]!=null&&b.b[j][i].d!=b.w){
						b.b[j][i]=null;
					}
				}
			}
			int ds=0;
			for (int i=0;i<2;i++){
				for (int j=0;j<b.dpli[i];j++){
					if (b.dpl[i][j].d!=b.w){
						b.dpl[i][j]=null;
						ds=i;
					}
				}
			}
			b.dpli[ds]=0;
		}
		return b;
	}



	private List<int[]> _possible_moves(Board b,int t){
		List<int[]> pm=new ArrayList<int[]>();
		for (int y=0;y<4;y++){
			for (int x=0;x<3;x++){
				Piece p=b.b[y][x];
				if (p!=null&&p.d==t){
					for (int i=-1;i<=1;i++){
						for (int j=-1;j<=1;j++){
							if (i==0&&j==0){
								continue;
							}
							if (p._can_move(i,j)==1){
								pm.add(new int[]{x,y,x+i,y+j});
							}
						}
					}
				}
			}
		}
		return pm;
	}



	private double _eval_board(Board b,int d){
		if (b.w!=0){
			return b.w*10000000*this.t;
		}
		double sc=0;
		for (int y=0;y<4;y++){
			for (int x=0;x<3;x++){
				Piece p=b.b[y][x];
				if (p!=null){
					sc+=p._eval_value()*(p.d==this.t?-1:1);
				}
			}
		}
		for (int i=0;i<2;i++){
			for (int j=0;j<b.dpli[i];j++){
				Piece p=b.dpl[i][j];
				sc+=p._eval_value()*1.5*(p.d==this.t?-1:1);
			}
		}
		return sc;
	}



	private double _step_mult_f(int d){
		double n=2;
		return Math.pow(d+1,n)/Math.pow(AI_MINMAX_DEPTH+1,n);
	}
}