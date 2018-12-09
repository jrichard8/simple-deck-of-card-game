import { IGame } from 'app/shared/model//game.model';
import { ISuit } from 'app/shared/model//suit.model';

export interface IDeck {
    id?: number;
    suits?: ISuit[];
    game_id?: number;
}

export class Deck implements IDeck {
    constructor(public id?: number, public game?: IGame, public suits?: ISuit[], public game_id?: number) {}
}
