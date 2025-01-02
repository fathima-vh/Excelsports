export interface MessageDto {
    id?: number;
    athleteId: number;
    coachId: number;
    athleteName?:string;
    coachName?:string;
    role: string;
    content: string;
    sentAt: Date;
    read: boolean;
}