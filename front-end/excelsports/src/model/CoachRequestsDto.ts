export interface CoachRequestsDto {
  requestId:number;
  coachId: number;
  athleteId: number;
  athleteName: string;
  athleteSport: string;
  status: string; // Or use an enum if preferred
  requestedAt: string; // ISO timestamp
}