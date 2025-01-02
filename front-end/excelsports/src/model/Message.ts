export interface Message {
    id: number;
    athleteId: number;
    coachId: number;
    role: string;
    content: string;
    sentAt: Date;
    read: boolean;
}