export interface ICoordinates {
  x: number;
  y: number;
}

export interface ILocation {
  x: number;
  y: number;
  z: number;
  name: string;
}

export interface IPerson {
  name: string;
  eyeColor: ColorEnum;
  hairColor: ColorEnum;
  location: ILocation;
  birthday: string;
}

export enum ColorEnum {
  GREEN = "GREEN",
  RED = "RED",
  BLUE = "BLUE",
  YELLOW = "YELLOW",
  WHITE = "WHITE",
}

export enum CountryEnum {
  RUSSIA = "RUSSIA",
  GERMANY = "GERMANY",
  INDEA = "INDIA",
  NORTH_KOREA = "NORTH_KOREA",
  JAPAN = "JAPAN",
}

export enum MpaaRatingEnum {
  G = "G",
  PG = "PG",
  PG_13 = "PG_13",
  R = "R",
  NC_17 = "NC_17",
}

export enum MovieGenreEnum {
  ADVENTURE = "ADVENTURE",
  TRAGEDY = "TRAGEDY",
  SCIENCE_FICTION = "SCIENCE_FICTION",
}

export interface IMovie {
  name: string;
  coordinates: ICoordinates;
  creationDate: string;
  oscarsCount: number;
  budget?: number;
  totalBoxOffice: number;
  mpaaRating?: MpaaRatingEnum;
  director?: IPerson;
  screenwriter: IPerson;
  operator?: IPerson;
  length?: number;
  goldenPalmCount: number;
  usaBoxOffice: number;
  tagline?: string;
  genre: MovieGenreEnum;
  // createdUser: string;
  isPublic: boolean;
}

export interface IMovieCreate {
  name: string;
  coordinates: ICoordinates;
  creationDate: string;
  oscarsCount: number;
  budget?: number;
  totalBoxOffice: number;
  mpaaRating?: MpaaRatingEnum;
  director?: IPerson;
  screenwriter: IPerson;
  operator?: IPerson;
  length?: number;
  goldenPalmCount: number;
  usaBoxOffice: number;
  tagline?: string;
  genre: MovieGenreEnum;
  // createdUser: string;
  isPublic: boolean;
}

export interface IMovieUpdate {
  id: number;
  name: string;
  coordinates: ICoordinates;
  creationDate: string;
  oscarsCount: number;
  budget?: number;
  totalBoxOffice: number;
  mpaaRating?: MpaaRatingEnum;
  director?: IPerson;
  screenwriter: IPerson;
  operator?: IPerson;
  length?: number;
  goldenPalmCount: number;
  usaBoxOffice: number;
  tagline?: string;
  genre: MovieGenreEnum;
  createdUser: string;
  isPublic: boolean;
}

export const mapFormDataToIMovie = (formData: any): IMovieCreate => {
  return {
    name: formData.name,
    coordinates: {
      x: Number(formData.coordinates.x),
      y: Number(formData.coordinates.y),
    },
    creationDate: formData.creationDate,
    oscarsCount: Number(formData.oscarsCount) || 0,
    budget: Number(formData.budget) || undefined,
    totalBoxOffice: Number(formData.totalBoxOffice),
    mpaaRating: formData.mpaaRating as MpaaRatingEnum,
    director: {
      name: formData.director.name,
      eyeColor: formData.director.eyeColor as ColorEnum,
      hairColor: formData.director.hairColor as ColorEnum,
      location: {
        x: Number(formData.director.location.x),
        y: Number(formData.director.location.y),
        z: Number(formData.director.location.z),
        name: formData.director.location.name,
      },
      birthday: formData.director.birthday,
    },
    screenwriter: {
      name: formData.screenwriter.name,
      eyeColor: formData.screenwriter.eyeColor as ColorEnum,
      hairColor: formData.screenwriter.hairColor as ColorEnum,
      location: {
        x: Number(formData.screenwriter.location.x),
        y: Number(formData.screenwriter.location.y),
        z: Number(formData.screenwriter.location.z),
        name: formData.director.location.name,
      },
      birthday: formData.screenwriter.birthday,
    },
    operator: {
      name: formData.operator.name,
      eyeColor: formData.operator.eyeColor as ColorEnum,
      hairColor: formData.operator.hairColor as ColorEnum,
      location: {
        x: Number(formData.operator.location.x),
        y: Number(formData.operator.location.y),
        z: Number(formData.operator.location.z),
        name: formData.director.location.name,
      },
      birthday: formData.operator.birthday,
    },
    length: Number(formData.length) || undefined,
    goldenPalmCount: Number(formData.goldenPalmCount) || 0,
    usaBoxOffice: Number(formData.usaBoxOffice) || 0,
    tagline: formData.tagline || undefined,
    genre: formData.genre as MovieGenreEnum,
    isPublic: formData.isPublic || false,
  };
};

export const mapFormDataToIMovieUpdate = (formData: any): IMovieUpdate => {
  return {
    id: formData.id,
    name: formData.name,
    coordinates: {
      x: Number(formData.coordinates.x),
      y: Number(formData.coordinates.y),
    },
    creationDate: formData.creationDate,
    oscarsCount: Number(formData.oscarsCount) || 0,
    budget: Number(formData.budget) || undefined,
    totalBoxOffice: Number(formData.totalBoxOffice),
    mpaaRating: formData.mpaaRating as MpaaRatingEnum,
    director: {
      name: formData.director.name,
      eyeColor: formData.director.eyeColor as ColorEnum,
      hairColor: formData.director.hairColor as ColorEnum,
      location: {
        x: Number(formData.director.location.x),
        y: Number(formData.director.location.y),
        z: Number(formData.director.location.z),
        name: formData.director.location.name,
      },
      birthday: formData.director.birthday,
    },
    screenwriter: {
      name: formData.screenwriter.name,
      eyeColor: formData.screenwriter.eyeColor as ColorEnum,
      hairColor: formData.screenwriter.hairColor as ColorEnum,
      location: {
        x: Number(formData.screenwriter.location.x),
        y: Number(formData.screenwriter.location.y),
        z: Number(formData.screenwriter.location.z),
        name: formData.director.location.name,
      },
      birthday: formData.screenwriter.birthday,
    },
    operator: {
      name: formData.operator.name,
      eyeColor: formData.operator.eyeColor as ColorEnum,
      hairColor: formData.operator.hairColor as ColorEnum,
      location: {
        x: Number(formData.operator.location.x),
        y: Number(formData.operator.location.y),
        z: Number(formData.operator.location.z),
        name: formData.director.location.name,
      },
      birthday: formData.operator.birthday,
    },
    length: Number(formData.length) || undefined,
    goldenPalmCount: Number(formData.goldenPalmCount) || 0,
    usaBoxOffice: Number(formData.usaBoxOffice) || 0,
    tagline: formData.tagline || undefined,
    genre: formData.genre as MovieGenreEnum,
    createdUser: formData.createdUser,
    isPublic: formData.isPublic || false,
  };
};
