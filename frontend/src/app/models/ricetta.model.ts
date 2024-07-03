export interface Ricetta {
  [key: string]: any;
  id? : number;
  authorId: number;
  authorName: string;
  description: string;
  immagine1: string;
  immagine2: string;
  immagine3: string;
  listaIngredienti: any[];
  title: string;
  immagineUrl?: string;
  immagineUrl2?: string;
  immagineUrl3?: string;
}


