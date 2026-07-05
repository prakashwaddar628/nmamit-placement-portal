import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_animate/flutter_animate.dart';
import 'package:go_router/go_router.dart';
import 'providers/student_providers.dart';
import '../domain/student_models.dart';
import '../data/student_repository.dart';

class JobDrivesScreen extends ConsumerWidget {
  const JobDrivesScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final drivesAsync = ref.watch(jobDrivesProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Open Job Drives'),
      ),
      body: RefreshIndicator(
        onRefresh: () => ref.refresh(jobDrivesProvider.future),
        child: drivesAsync.when(
          data: (drives) {
            if (drives.isEmpty) {
              return const Center(child: Text('No open job drives available.'));
            }
            return ListView.builder(
              padding: const EdgeInsets.all(16),
              itemCount: drives.length,
              itemBuilder: (context, index) {
                final drive = drives[index];
                return _JobDriveCard(drive: drive, delay: index * 100);
              },
            );
          },
          loading: () => const Center(child: CircularProgressIndicator()),
          error: (error, _) => Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Icon(Icons.error_outline, size: 48, color: Colors.red),
                const SizedBox(height: 16),
                Text('Failed to load job drives:\n$error', textAlign: TextAlign.center),
                const SizedBox(height: 16),
                FilledButton.tonal(
                  onPressed: () => ref.refresh(jobDrivesProvider),
                  child: const Text('Retry'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class _JobDriveCard extends ConsumerWidget {
  final JobDrive drive;
  final int delay;

  const _JobDriveCard({required this.drive, required this.delay});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final colorScheme = Theme.of(context).colorScheme;
    final textTheme = Theme.of(context).textTheme;

    return Card(
      margin: const EdgeInsets.only(bottom: 16),
      child: InkWell(
        borderRadius: BorderRadius.circular(16),
        onTap: () {
          _showJobDetails(context, drive, ref);
        },
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    width: 48,
                    height: 48,
                    decoration: BoxDecoration(
                      color: colorScheme.primaryContainer,
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Center(
                      child: Text(
                        drive.companyName.substring(0, 1).toUpperCase(),
                        style: textTheme.titleLarge?.copyWith(
                          color: colorScheme.onPrimaryContainer,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(width: 16),
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          drive.jobRole,
                          style: textTheme.titleMedium?.copyWith(
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        Text(
                          drive.companyName,
                          style: textTheme.bodyMedium?.copyWith(
                            color: colorScheme.onSurfaceVariant,
                          ),
                        ),
                      ],
                    ),
                  ),
                  Container(
                    padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                    decoration: BoxDecoration(
                      color: Colors.green.withOpacity(0.1),
                      borderRadius: BorderRadius.circular(8),
                    ),
                    child: Text(
                      '${drive.packageLpa} LPA',
                      style: textTheme.labelSmall?.copyWith(
                        color: Colors.green[700],
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 16),
              Row(
                children: [
                  Icon(Icons.location_on_outlined, size: 16, color: colorScheme.onSurfaceVariant),
                  const SizedBox(width: 4),
                  Text(drive.location, style: textTheme.bodySmall),
                  const SizedBox(width: 16),
                  Icon(Icons.event_outlined, size: 16, color: colorScheme.onSurfaceVariant),
                  const SizedBox(width: 4),
                  Text(drive.driveDate, style: textTheme.bodySmall),
                ],
              ),
              const Divider(height: 24),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    'Apply by ${drive.registrationDeadline}',
                    style: textTheme.bodySmall?.copyWith(
                      color: colorScheme.error,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                  FilledButton.tonal(
                    onPressed: () => _showJobDetails(context, drive, ref),
                    style: FilledButton.styleFrom(
                      minimumSize: const Size(80, 36),
                      padding: const EdgeInsets.symmetric(horizontal: 16),
                    ),
                    child: const Text('View Details'),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    ).animate().fadeIn(delay: delay.ms).slideY(begin: 0.1);
  }

  void _showJobDetails(BuildContext context, JobDrive drive, WidgetRef ref) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      useSafeArea: true,
      builder: (context) => _JobDriveDetailsSheet(drive: drive),
    );
  }
}

class _JobDriveDetailsSheet extends ConsumerStatefulWidget {
  final JobDrive drive;
  const _JobDriveDetailsSheet({required this.drive});

  @override
  ConsumerState<_JobDriveDetailsSheet> createState() => _JobDriveDetailsSheetState();
}

class _JobDriveDetailsSheetState extends ConsumerState<_JobDriveDetailsSheet> {
  bool _isApplying = false;

  Future<void> _apply() async {
    setState(() => _isApplying = true);
    try {
      await ref.read(studentRepositoryProvider).applyForJob(widget.drive.id);
      if (mounted) {
        context.pop();
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Successfully applied for the job!'), backgroundColor: Colors.green),
        );
        ref.refresh(studentDashboardProvider);
        ref.refresh(myApplicationsProvider);
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(e.toString()), backgroundColor: Colors.red),
        );
      }
    } finally {
      if (mounted) setState(() => _isApplying = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    final colorScheme = Theme.of(context).colorScheme;

    return Container(
      padding: const EdgeInsets.all(24),
      decoration: BoxDecoration(
        color: Theme.of(context).scaffoldBackgroundColor,
        borderRadius: const BorderRadius.vertical(top: Radius.circular(24)),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: [
          Center(
            child: Container(
              width: 40,
              height: 4,
              decoration: BoxDecoration(
                color: colorScheme.outlineVariant,
                borderRadius: BorderRadius.circular(2),
              ),
            ),
          ),
          const SizedBox(height: 24),
          Text(widget.drive.jobRole, style: textTheme.headlineSmall?.copyWith(fontWeight: FontWeight.bold)),
          Text(widget.drive.companyName, style: textTheme.titleMedium?.copyWith(color: colorScheme.primary)),
          const SizedBox(height: 24),
          _buildInfoRow(Icons.monetization_on_outlined, 'Package', '${widget.drive.packageLpa} LPA'),
          _buildInfoRow(Icons.location_on_outlined, 'Location', widget.drive.location),
          _buildInfoRow(Icons.event_available_outlined, 'Drive Date', widget.drive.driveDate),
          _buildInfoRow(Icons.school_outlined, 'Min CGPA', widget.drive.minimumCgpa.toString()),
          _buildInfoRow(Icons.history_outlined, 'Allowed Backlogs', widget.drive.allowedBacklogs.toString()),
          const SizedBox(height: 24),
          Text('Description', style: textTheme.titleMedium?.copyWith(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          Text(widget.drive.description ?? 'No description provided.', style: textTheme.bodyMedium),
          const SizedBox(height: 32),
          SizedBox(
            width: double.infinity,
            child: FilledButton(
              onPressed: _isApplying ? null : _apply,
              child: _isApplying
                  ? const SizedBox(height: 20, width: 20, child: CircularProgressIndicator(strokeWidth: 2, color: Colors.white))
                  : const Text('Apply Now'),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildInfoRow(IconData icon, String label, String value) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12),
      child: Row(
        children: [
          Icon(icon, size: 20, color: Theme.of(context).colorScheme.onSurfaceVariant),
          const SizedBox(width: 12),
          Text(label, style: Theme.of(context).textTheme.bodyMedium?.copyWith(color: Theme.of(context).colorScheme.onSurfaceVariant)),
          const Spacer(),
          Text(value, style: Theme.of(context).textTheme.titleSmall?.copyWith(fontWeight: FontWeight.bold)),
        ],
      ),
    );
  }
}
