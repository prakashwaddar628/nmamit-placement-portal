import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:flutter_animate/flutter_animate.dart';
import '../../auth/presentation/auth_provider.dart';
import 'providers/student_providers.dart';

class StudentDashboardScreen extends ConsumerWidget {
  const StudentDashboardScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final authState = ref.watch(authNotifierProvider);
    final String email = authState is AuthAuthenticated ? authState.email : '';
    final dashboardAsync = ref.watch(studentDashboardProvider);
    final colorScheme = Theme.of(context).colorScheme;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Dashboard'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () {
              ref.read(authNotifierProvider.notifier).logout();
            },
          ),
        ],
      ),
      body: RefreshIndicator(
        onRefresh: () => ref.refresh(studentDashboardProvider.future),
        child: SingleChildScrollView(
          physics: const AlwaysScrollableScrollPhysics(),
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Welcome,',
                style: Theme.of(context).textTheme.titleLarge?.copyWith(
                      color: colorScheme.onSurfaceVariant,
                    ),
              ).animate().fadeIn().slideX(),
              Text(
                email,
                style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                      fontWeight: FontWeight.bold,
                    ),
              ).animate().fadeIn(delay: 100.ms).slideX(),
              const SizedBox(height: 24),
              dashboardAsync.when(
                data: (dashboard) => Column(
                  children: [
                    _buildAlerts(context, dashboard),
                    const SizedBox(height: 24),
                    GridView.count(
                      crossAxisCount: 2,
                      crossAxisSpacing: 16,
                      mainAxisSpacing: 16,
                      shrinkWrap: true,
                      physics: const NeverScrollableScrollPhysics(),
                      children: [
                        _StatCard(
                          title: 'Applications',
                          value: dashboard.applications.toString(),
                          icon: Icons.assignment_outlined,
                          color: Colors.blue,
                          delay: 200,
                        ),
                        _StatCard(
                          title: 'Upcoming Drives',
                          value: dashboard.upcomingDrives.toString(),
                          icon: Icons.work_outline,
                          color: Colors.orange,
                          delay: 300,
                        ),
                        _StatCard(
                          title: 'Interviews',
                          value: dashboard.interviews.toString(),
                          icon: Icons.people_outline,
                          color: Colors.purple,
                          delay: 400,
                        ),
                        _StatCard(
                          title: 'Selected',
                          value: dashboard.selected.toString(),
                          icon: Icons.check_circle_outline,
                          color: Colors.green,
                          delay: 500,
                        ),
                      ],
                    ),
                  ],
                ),
                loading: () => const Center(
                  child: Padding(
                    padding: EdgeInsets.all(32.0),
                    child: CircularProgressIndicator(),
                  ),
                ),
                error: (error, _) => Center(
                  child: Padding(
                    padding: const EdgeInsets.all(32.0),
                    child: Column(
                      children: [
                        const Icon(Icons.error_outline, color: Colors.red, size: 48),
                        const SizedBox(height: 16),
                        Text('Failed to load dashboard: $error', textAlign: TextAlign.center),
                        const SizedBox(height: 16),
                        FilledButton.tonal(
                          onPressed: () => ref.refresh(studentDashboardProvider),
                          child: const Text('Retry'),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildAlerts(BuildContext context, dashboard) {
    final List<Widget> alerts = [];
    
    if (!dashboard.profileCompleted) {
      alerts.add(
        _AlertCard(
          message: 'Complete your profile to apply for jobs.',
          buttonText: 'Update Profile',
          icon: Icons.person_outline,
          color: Colors.orange,
          onTap: () => context.go('/student/profile'),
        ),
      );
    }
    
    if (!dashboard.resumeUploaded) {
      alerts.add(
        _AlertCard(
          message: 'Upload your resume to be visible to companies.',
          buttonText: 'Upload Resume',
          icon: Icons.file_upload_outlined,
          color: Colors.red,
          onTap: () => context.go('/student/resume'),
        ),
      );
    }

    if (alerts.isEmpty) {
      alerts.add(
        _AlertCard(
          message: 'Your profile is 100% complete.',
          buttonText: 'View Jobs',
          icon: Icons.check_circle,
          color: Colors.green,
          onTap: () => context.go('/student/job-drives'),
        ),
      );
    }

    return Column(
      children: alerts.animate(interval: 100.ms).fadeIn().slideY(begin: 0.2),
    );
  }
}

class _StatCard extends StatelessWidget {
  final String title;
  final String value;
  final IconData icon;
  final Color color;
  final int delay;

  const _StatCard({
    required this.title,
    required this.value,
    required this.icon,
    required this.color,
    required this.delay,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.zero,
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Container(
              padding: const EdgeInsets.all(8),
              decoration: BoxDecoration(
                color: color.withOpacity(0.1),
                borderRadius: BorderRadius.circular(12),
              ),
              child: Icon(icon, color: color, size: 28),
            ),
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  value,
                  style: Theme.of(context).textTheme.headlineMedium?.copyWith(
                        fontWeight: FontWeight.bold,
                        color: color,
                      ),
                ),
                Text(
                  title,
                  style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                        fontWeight: FontWeight.w500,
                      ),
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                ),
              ],
            ),
          ],
        ),
      ),
    ).animate().scale(delay: delay.ms, duration: 400.ms, curve: Curves.easeOutBack);
  }
}

class _AlertCard extends StatelessWidget {
  final String message;
  final String buttonText;
  final IconData icon;
  final Color color;
  final VoidCallback onTap;

  const _AlertCard({
    required this.message,
    required this.buttonText,
    required this.icon,
    required this.color,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.only(bottom: 12),
      color: color.withOpacity(0.1),
      elevation: 0,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(16),
        side: BorderSide(color: color.withOpacity(0.3)),
      ),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Row(
          children: [
            Icon(icon, color: color, size: 32),
            const SizedBox(width: 16),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    message,
                    style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                          color: Theme.of(context).colorScheme.onSurface,
                          fontWeight: FontWeight.w500,
                        ),
                  ),
                  const SizedBox(height: 8),
                  InkWell(
                    onTap: onTap,
                    child: Text(
                      buttonText,
                      style: TextStyle(
                        color: color,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
