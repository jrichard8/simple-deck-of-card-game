import { IGame } from 'app/shared/model//game.model';
import { IHand } from 'app/shared/model//hand.model';

export interface IPlayer {
    id?: number;
    name?: string;
    sumOfCardValue?: number;
    game?: IGame;
    hand?: IHand;
}

export class Player implements IPlayer {
    constructor(public id?: number, public name?: string, public sumOfCardValue?: number, public game?: IGame, public hand?: IHand) {}
}
