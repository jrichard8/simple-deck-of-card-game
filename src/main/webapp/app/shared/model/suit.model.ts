import { IDeck } from 'app/shared/model//deck.model';
import { ICard } from 'app/shared/model//card.model';

export const enum SuitEnum {
    CLUB = 'CLUB',
    DIAMOND = 'DIAMOND',
    SPADE = 'SPADE',
    HEART = 'HEART'
}

export interface ISuit {
    id?: number;
    suitType?: SuitEnum;
    deck?: IDeck;
    suits?: ICard[];
}

export class Suit implements ISuit {
    constructor(public id?: number, public suitType?: SuitEnum, public deck?: IDeck, public suits?: ICard[]) {}
}
