import { ISuit } from 'app/shared/model//suit.model';
import { IHand } from 'app/shared/model//hand.model';

export const enum SuitEnum {
    CLUB = 'CLUB',
    DIAMOND = 'DIAMOND',
    SPADE = 'SPADE',
    HEART = 'HEART'
}

export const enum ValueEnum {
    ACE = 'ACE',
    TWO = 'TWO',
    THREE = 'THREE',
    FOUR = 'FOUR',
    FIVE = 'FIVE',
    SIX = 'SIX',
    SEVEN = 'SEVEN',
    EIGHT = 'EIGHT',
    NINE = 'NINE',
    TEN = 'TEN',
    JACK = 'JACK',
    QUEEN = 'QUEEN',
    KING = 'KING'
}

export interface ICard {
    id?: number;
    suitType?: SuitEnum;
    value?: ValueEnum;
    suit?: ISuit;
    hand?: IHand;
}

export class Card implements ICard {
    constructor(public id?: number, public suitType?: SuitEnum, public value?: ValueEnum, public suit?: ISuit, public hand?: IHand) {}
}
