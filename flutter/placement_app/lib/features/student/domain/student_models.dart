class JobDrive {
  final int id;
  final String companyName;
  final String jobRole;
  final String jobType;
  final double packageLpa;
  final String location;
  final String driveDate;
  final String registrationDeadline;
  final double minimumCgpa;
  final int allowedBacklogs;
  final String? description;
  final String status;

  const JobDrive({
    required this.id,
    required this.companyName,
    required this.jobRole,
    required this.jobType,
    required this.packageLpa,
    required this.location,
    required this.driveDate,
    required this.registrationDeadline,
    required this.minimumCgpa,
    required this.allowedBacklogs,
    this.description,
    required this.status,
  });

  factory JobDrive.fromJson(Map<String, dynamic> json) {
    return JobDrive(
      id: (json['id'] as num).toInt(),
      companyName: json['companyName'] as String,
      jobRole: json['jobRole'] as String,
      jobType: json['jobType'] as String? ?? 'FULL_TIME',
      packageLpa: (json['packageLpa'] as num?)?.toDouble() ?? 0,
      location: json['location'] as String? ?? '',
      driveDate: json['driveDate'] as String? ?? '',
      registrationDeadline: json['registrationDeadline'] as String? ?? '',
      minimumCgpa: (json['minimumCgpa'] as num?)?.toDouble() ?? 0,
      allowedBacklogs: (json['allowedBacklogs'] as num?)?.toInt() ?? 0,
      description: json['description'] as String?,
      status: json['status'] as String? ?? 'OPEN',
    );
  }

  bool get isOpen => status == 'OPEN';
}

class Application {
  final int id;
  final String companyName;
  final String jobRole;
  final String status;
  final String appliedAt;

  const Application({
    required this.id,
    required this.companyName,
    required this.jobRole,
    required this.status,
    required this.appliedAt,
  });

  factory Application.fromJson(Map<String, dynamic> json) {
    return Application(
      id: (json['id'] as num).toInt(),
      companyName: json['companyName'] as String,
      jobRole: json['jobRole'] as String,
      status: json['status'] as String,
      appliedAt: json['appliedAt'] as String? ?? '',
    );
  }
}

class StudentProfile {
  final String? collegeEmail;
  final String? usn;
  final String? fullName;
  final String? mobile;
  final String? branch;
  final String? department;
  final double? cgpa;
  final int? activeBacklogs;
  final String? city;
  final String? state;
  final String? linkedinUrl;
  final String? githubUrl;

  const StudentProfile({
    this.collegeEmail,
    this.usn,
    this.fullName,
    this.mobile,
    this.branch,
    this.department,
    this.cgpa,
    this.activeBacklogs,
    this.city,
    this.state,
    this.linkedinUrl,
    this.githubUrl,
  });

  factory StudentProfile.fromJson(Map<String, dynamic> json) {
    return StudentProfile(
      collegeEmail: json['collegeEmail'] as String?,
      usn: json['usn'] as String?,
      fullName: json['fullName'] as String?,
      mobile: json['mobile'] as String?,
      branch: json['branch'] as String?,
      department: json['department'] as String?,
      cgpa: (json['cgpa'] as num?)?.toDouble(),
      activeBacklogs: (json['activeBacklogs'] as num?)?.toInt(),
      city: json['city'] as String?,
      state: json['state'] as String?,
      linkedinUrl: json['linkedinUrl'] as String?,
      githubUrl: json['githubUrl'] as String?,
    );
  }
}
