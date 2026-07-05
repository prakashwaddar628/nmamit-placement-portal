import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_animate/flutter_animate.dart';
import 'providers/student_providers.dart';
import '../data/student_repository.dart';

class StudentProfileScreen extends ConsumerStatefulWidget {
  const StudentProfileScreen({super.key});

  @override
  ConsumerState<StudentProfileScreen> createState() => _StudentProfileScreenState();
}

class _StudentProfileScreenState extends ConsumerState<StudentProfileScreen> {
  final _formKey = GlobalKey<FormState>();
  bool _isEditing = false;
  bool _isSaving = false;

  final _fullNameCtrl = TextEditingController();
  final _usnCtrl = TextEditingController();
  final _mobileCtrl = TextEditingController();
  final _branchCtrl = TextEditingController();
  final _departmentCtrl = TextEditingController();
  final _cgpaCtrl = TextEditingController();
  final _backlogsCtrl = TextEditingController();
  final _cityCtrl = TextEditingController();
  final _stateCtrl = TextEditingController();

  @override
  void dispose() {
    _fullNameCtrl.dispose();
    _usnCtrl.dispose();
    _mobileCtrl.dispose();
    _branchCtrl.dispose();
    _departmentCtrl.dispose();
    _cgpaCtrl.dispose();
    _backlogsCtrl.dispose();
    _cityCtrl.dispose();
    _stateCtrl.dispose();
    super.dispose();
  }

  void _populateControllers(profile) {
    _fullNameCtrl.text = profile.fullName ?? '';
    _usnCtrl.text = profile.usn ?? '';
    _mobileCtrl.text = profile.mobile ?? '';
    _branchCtrl.text = profile.branch ?? '';
    _departmentCtrl.text = profile.department ?? '';
    _cgpaCtrl.text = profile.cgpa?.toString() ?? '';
    _backlogsCtrl.text = profile.activeBacklogs?.toString() ?? '';
    _cityCtrl.text = profile.city ?? '';
    _stateCtrl.text = profile.state ?? '';
  }

  Future<void> _saveProfile() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() => _isSaving = true);
    try {
      await ref.read(studentRepositoryProvider).updateProfile({
        'fullName': _fullNameCtrl.text,
        'usn': _usnCtrl.text,
        'mobile': _mobileCtrl.text,
        'branch': _branchCtrl.text,
        'department': _departmentCtrl.text,
        'cgpa': double.tryParse(_cgpaCtrl.text),
        'activeBacklogs': int.tryParse(_backlogsCtrl.text),
        'city': _cityCtrl.text,
        'state': _stateCtrl.text,
      });

      if (mounted) {
        setState(() => _isEditing = false);
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Profile updated successfully'), backgroundColor: Colors.green),
        );
        ref.refresh(studentProfileProvider);
        ref.refresh(studentDashboardProvider);
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text(e.toString()), backgroundColor: Colors.red),
        );
      }
    } finally {
      if (mounted) setState(() => _isSaving = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final profileAsync = ref.watch(studentProfileProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('My Profile'),
        actions: [
          if (!_isEditing)
            IconButton(
              icon: const Icon(Icons.edit),
              onPressed: () {
                profileAsync.whenData((profile) {
                  _populateControllers(profile);
                  setState(() => _isEditing = true);
                });
              },
            )
          else
            IconButton(
              icon: const Icon(Icons.close),
              onPressed: () => setState(() => _isEditing = false),
            ),
        ],
      ),
      body: profileAsync.when(
        data: (profile) {
          if (!_isEditing) {
            _populateControllers(profile);
          }

          return SingleChildScrollView(
            padding: const EdgeInsets.all(16),
            child: Form(
              key: _formKey,
              child: Column(
                children: [
                  Center(
                    child: Container(
                      width: 100,
                      height: 100,
                      decoration: BoxDecoration(
                        color: Theme.of(context).colorScheme.primaryContainer,
                        shape: BoxShape.circle,
                      ),
                      child: Center(
                        child: Text(
                          (_fullNameCtrl.text.isNotEmpty ? _fullNameCtrl.text[0] : '?').toUpperCase(),
                          style: Theme.of(context).textTheme.headlineLarge?.copyWith(
                                color: Theme.of(context).colorScheme.onPrimaryContainer,
                              ),
                        ),
                      ),
                    ).animate().scale(),
                  ),
                  const SizedBox(height: 32),
                  _buildSection('Personal Information', [
                    _buildField('Full Name', _fullNameCtrl, Icons.person_outline),
                    _buildField('USN', _usnCtrl, Icons.badge_outlined),
                    _buildField('Mobile', _mobileCtrl, Icons.phone_outlined, isNumber: true),
                  ]),
                  _buildSection('Academic Information', [
                    _buildField('Department', _departmentCtrl, Icons.domain_outlined),
                    _buildField('Branch', _branchCtrl, Icons.class_outlined),
                    _buildField('CGPA', _cgpaCtrl, Icons.grade_outlined, isNumber: true),
                    _buildField('Active Backlogs', _backlogsCtrl, Icons.history_outlined, isNumber: true),
                  ]),
                  _buildSection('Location', [
                    _buildField('City', _cityCtrl, Icons.location_city_outlined),
                    _buildField('State', _stateCtrl, Icons.map_outlined),
                  ]),
                  if (_isEditing) ...[
                    const SizedBox(height: 24),
                    SizedBox(
                      width: double.infinity,
                      child: FilledButton(
                        onPressed: _isSaving ? null : _saveProfile,
                        child: _isSaving
                            ? const SizedBox(
                                height: 20,
                                width: 20,
                                child: CircularProgressIndicator(strokeWidth: 2, color: Colors.white),
                              )
                            : const Text('Save Profile'),
                      ),
                    ),
                    const SizedBox(height: 24),
                  ]
                ],
              ),
            ),
          );
        },
        loading: () => const Center(child: CircularProgressIndicator()),
        error: (error, _) => Center(child: Text('Error: $error')),
      ),
    );
  }

  Widget _buildSection(String title, List<Widget> children) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          title,
          style: Theme.of(context).textTheme.titleMedium?.copyWith(
                fontWeight: FontWeight.bold,
                color: Theme.of(context).colorScheme.primary,
              ),
        ),
        const SizedBox(height: 16),
        ...children.map((child) => Padding(
              padding: const EdgeInsets.only(bottom: 16),
              child: child,
            )),
        const Divider(height: 32),
      ],
    ).animate().fadeIn().slideY(begin: 0.1);
  }

  Widget _buildField(String label, TextEditingController controller, IconData icon, {bool isNumber = false}) {
    return TextFormField(
      controller: controller,
      enabled: _isEditing,
      keyboardType: isNumber ? TextInputType.number : TextInputType.text,
      decoration: InputDecoration(
        labelText: label,
        prefixIcon: Icon(icon),
      ),
      validator: (v) {
        if (v == null || v.trim().isEmpty) return '$label is required';
        return null;
      },
    );
  }
}
