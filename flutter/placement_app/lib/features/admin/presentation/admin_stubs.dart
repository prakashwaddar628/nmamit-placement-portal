import 'package:flutter/material.dart';

class AdminStudentsScreen extends StatelessWidget {
  const AdminStudentsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return _buildStub(context, 'Students Management', Icons.people_outline);
  }
}

class AdminCompaniesScreen extends StatelessWidget {
  const AdminCompaniesScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return _buildStub(context, 'Companies Management', Icons.business_outlined);
  }
}

class AdminJobDrivesScreen extends StatelessWidget {
  const AdminJobDrivesScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return _buildStub(context, 'Job Drives Management', Icons.work_outline);
  }
}

class AdminApplicationsScreen extends StatelessWidget {
  const AdminApplicationsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return _buildStub(context, 'Applications Management', Icons.assignment_outlined);
  }
}

Widget _buildStub(BuildContext context, String title, IconData icon) {
  return Scaffold(
    appBar: AppBar(title: Text(title)),
    body: Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(icon, size: 64, color: Colors.grey),
          const SizedBox(height: 16),
          Text(title, style: Theme.of(context).textTheme.headlineSmall),
          const SizedBox(height: 8),
          const Text('Mobile UI under construction. Please use web admin portal.', textAlign: TextAlign.center),
        ],
      ),
    ),
  );
}
