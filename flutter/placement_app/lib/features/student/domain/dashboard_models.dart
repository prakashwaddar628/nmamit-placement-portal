class StudentDashboard {
  final bool profileCompleted;
  final bool resumeUploaded;
  final int applications;
  final int selected;
  final int interviews;
  final int upcomingDrives;

  const StudentDashboard({
    required this.profileCompleted,
    required this.resumeUploaded,
    required this.applications,
    required this.selected,
    required this.interviews,
    required this.upcomingDrives,
  });

  factory StudentDashboard.fromJson(Map<String, dynamic> json) {
    return StudentDashboard(
      profileCompleted: json['profileCompleted'] as bool? ?? false,
      resumeUploaded: json['resumeUploaded'] as bool? ?? false,
      applications: (json['applications'] as num?)?.toInt() ?? 0,
      selected: (json['selected'] as num?)?.toInt() ?? 0,
      interviews: (json['interviews'] as num?)?.toInt() ?? 0,
      upcomingDrives: (json['upcomingDrives'] as num?)?.toInt() ?? 0,
    );
  }
}

class AdminDashboard {
  final int students;
  final int companies;
  final int jobDrives;
  final int applications;
  final int selectedStudents;
  final int openDrives;
  final int closedDrives;

  const AdminDashboard({
    required this.students,
    required this.companies,
    required this.jobDrives,
    required this.applications,
    required this.selectedStudents,
    required this.openDrives,
    required this.closedDrives,
  });

  factory AdminDashboard.fromJson(Map<String, dynamic> json) {
    return AdminDashboard(
      students: (json['students'] as num?)?.toInt() ?? 0,
      companies: (json['companies'] as num?)?.toInt() ?? 0,
      jobDrives: (json['jobDrives'] as num?)?.toInt() ?? 0,
      applications: (json['applications'] as num?)?.toInt() ?? 0,
      selectedStudents: (json['selectedStudents'] as num?)?.toInt() ?? 0,
      openDrives: (json['openDrives'] as num?)?.toInt() ?? 0,
      closedDrives: (json['closedDrives'] as num?)?.toInt() ?? 0,
    );
  }
}
