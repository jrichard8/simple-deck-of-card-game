import { IDeck } from 'app/shared/model//deck.model';
import { IPlayer } from 'app/shared/model//player.model';

export interface IGame {
    id?: number;
    name?: string;
    decks?: IDeck[];
    players?: IPlayer[];
}

export class Game implements IGame {
    constructor(public id?: number, public name?: string, public decks?: IDeck[], public players?: IPlayer[]) {}
}
