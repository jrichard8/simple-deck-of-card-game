import { ICard } from 'app/shared/model//card.model';

export interface IHand {
    id?: number;
    cards?: ICard[];
}

export class Hand implements IHand {
    constructor(public id?: number, public cards?: ICard[]) {}
}
