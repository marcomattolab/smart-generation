export interface Plugin {
    id: string;
    name: string;
    icon: string;
    version: string;
    category: string;
    description: string;
    tags: string[];
    enabled: boolean;
    configSchema: ConfigSchema;
}

export interface ConfigSchema {
    title: string;
    fields: Field[];
}

export interface Field {
    name: string;
    label: string;
    type: string;
    options?: any[] | any;
    default?: any;
    description?: string;
    min?: number;
    max?: number;
}

export const pluginRegistry: { [key: string]: Plugin } = {
    // Backend Plugins
    'spring-boot-oauth2': {
        id: 'spring-boot-oauth2',
        name: 'Spring Boot + OAuth2',
        icon: '☕',
        version: '3.2.0',
        category: 'backend',
        description: 'Complete Spring Boot setup with OAuth2 security, JWT tokens, and enterprise-grade authentication.',
        tags: ['java', 'spring', 'oauth2', 'security'],
        enabled: true,
        configSchema: {
            title: 'Spring Boot Configuration',
            fields: [
                {
                    name: 'javaVersion',
                    label: 'Java Version',
                    type: 'select',
                    options: ['11', '17', '21'],
                    default: '17',
                    description: 'Select the Java version for your project'
                },
                {
                    name: 'springBootVersion',
                    label: 'Spring Boot Version',
                    type: 'select',
                    options: ['3.0.x', '3.1.x', '3.2.x'],
                    default: '3.2.x'
                },
                {
                    name: 'includeSwagger',
                    label: 'Include Swagger Documentation',
                    type: 'checkbox',
                    default: true,
                    description: 'Generate OpenAPI 3.0 documentation with Swagger UI'
                },
                {
                    name: 'oauthProvider',
                    label: 'OAuth2 Provider',
                    type: 'radio',
                    options: [
                        { value: 'keycloak', label: 'Keycloak' },
                        { value: 'auth0', label: 'Auth0' },
                        { value: 'azure-ad', label: 'Azure AD' },
                        { value: 'google', label: 'Google OAuth' }
                    ],
                    default: 'keycloak'
                },
                {
                    name: 'maxConnections',
                    label: 'Max Database Connections',
                    type: 'range',
                    min: 5,
                    max: 100,
                    default: 20,
                    description: 'Maximum number of database connections in the pool'
                }
            ]
        }
    },
    'node-express-jwt': {
        id: 'node-express-jwt',
        name: 'Node.js + Express + JWT',
        icon: '🟢',
        version: '18.0.0',
        category: 'backend',
        description: 'Modern Node.js backend with Express framework, JWT authentication, and TypeScript support.',
        tags: ['nodejs', 'express', 'jwt', 'typescript'],
        enabled: false,
        configSchema: {
            title: 'Node.js Configuration',
            fields: [
                {
                    name: 'nodeVersion',
                    label: 'Node.js Version',
                    type: 'select',
                    options: ['16.x', '18.x', '20.x'],
                    default: '18.x'
                },
                {
                    name: 'useTypeScript',
                    label: 'Use TypeScript',
                    type: 'checkbox',
                    default: true,
                    description: 'Enable TypeScript for better type safety'
                },
                {
                    name: 'jwtSecret',
                    label: 'JWT Secret Key',
                    type: 'text',
                    default: 'your-super-secret-jwt-key',
                    description: 'Secret key for JWT token signing'
                }
            ]
        }
    },
    // Frontend Plugins
    'react-material-ui': {
        id: 'react-material-ui',
        name: 'React + Material-UI',
        icon: '⚛️',
        version: '18.2.0',
        category: 'frontend',
        description: 'React application with Material-UI components, Redux Toolkit, and modern development setup.',
        tags: ['react', 'material-ui', 'redux', 'typescript'],
        enabled: true,
        configSchema: {
            title: 'React Configuration',
            fields: [
                {
                    name: 'reactVersion',
                    label: 'React Version',
                    type: 'select',
                    options: ['17.x', '18.x'],
                    default: '18.x'
                },
                {
                    name: 'useTypeScript',
                    label: 'Use TypeScript',
                    type: 'checkbox',
                    default: true
                },
                {
                    name: 'stateManagement',
                    label: 'State Management',
                    type: 'radio',
                    options: [
                        { value: 'redux-toolkit', label: 'Redux Toolkit' },
                        { value: 'zustand', label: 'Zustand' },
                        { value: 'context', label: 'React Context' }
                    ],
                    default: 'redux-toolkit'
                },
                {
                    name: 'theme',
                    label: 'UI Theme',
                    type: 'select',
                    options: ['light', 'dark', 'auto'],
                    default: 'auto',
                    description: 'Default theme for the application'
                }
            ]
        }
    },
    'angular-bootstrap': {
        id: 'angular-bootstrap',
        name: 'Angular + Bootstrap',
        icon: '🅰️',
        version: '17.0.0',
        category: 'frontend',
        description: 'Angular application with Bootstrap styling, Angular Material, and enterprise-grade architecture.',
        tags: ['angular', 'bootstrap', 'material', 'typescript'],
        enabled: false,
        configSchema: {
            title: 'Angular Configuration',
            fields: [
                {
                    name: 'angularVersion',
                    label: 'Angular Version',
                    type: 'select',
                    options: ['15.x', '16.x', '17.x'],
                    default: '17.x'
                },
                {
                    name: 'includeAngularMaterial',
                    label: 'Include Angular Material',
                    type: 'checkbox',
                    default: true
                },
                {
                    name: 'routingStrategy',
                    label: 'Routing Strategy',
                    type: 'radio',
                    options: [
                        { value: 'hash', label: 'Hash Location Strategy' },
                        { value: 'path', label: 'Path Location Strategy' }
                    ],
                    default: 'path'
                }
            ]
        }
    },
    // Database Plugins
    'postgresql-config': {
        id: 'postgresql-config',
        name: 'PostgreSQL Database',
        icon: '🐘',
        version: '15.0',
        category: 'database',
        description: 'PostgreSQL database with connection pooling, migrations, and performance optimization.',
        tags: ['postgresql', 'database', 'sql', 'migrations'],
        enabled: true,
        configSchema: {
            title: 'PostgreSQL Configuration',
            fields: [
                {
                    name: 'version',
                    label: 'PostgreSQL Version',
                    type: 'select',
                    options: ['13.x', '14.x', '15.x', '16.x'],
                    default: '15.x'
                },
                {
                    name: 'enableSSL',
                    label: 'Enable SSL Connection',
                    type: 'checkbox',
                    default: true,
                    description: 'Use SSL for database connections in production'
                },
                {
                    name: 'maxConnections',
                    label: 'Max Connection Pool Size',
                    type: 'range',
                    min: 5,
                    max: 50,
                    default: 20
                },
                {
                    name: 'backupSchedule',
                    label: 'Backup Schedule',
                    type: 'select',
                    options: ['daily', 'weekly', 'monthly', 'disabled'],
                    default: 'daily',
                    description: 'Automated backup schedule'
                }
            ]
        }
    },
    // DevOps Plugins
    'docker-compose': {
        id: 'docker-compose',
        name: 'Docker + Compose',
        icon: '🐳',
        version: '24.0',
        category: 'devops',
        description: 'Complete Docker setup with multi-stage builds, compose configurations, and production optimization.',
        tags: ['docker', 'containerization', 'devops'],
        enabled: true,
        configSchema: {
            title: 'Docker Configuration',
            fields: [
                {
                    name: 'baseImage',
                    label: 'Base Image',
                    type: 'select',
                    options: ['alpine', 'ubuntu', 'debian'],
                    default: 'alpine',
                    description: 'Base Docker image for containers'
                },
                {
                    name: 'multiStage',
                    label: 'Multi-stage Build',
                    type: 'checkbox',
                    default: true,
                    description: 'Use multi-stage builds for smaller production images'
                },
                {
                    name: 'includeNginx',
                    label: 'Include Nginx Reverse Proxy',
                    type: 'checkbox',
                    default: true
                }
            ]
        }
    },
    'gitlab-ci': {
        id: 'gitlab-ci',
        name: 'GitLab CI/CD',
        icon: '🦊',
        version: '16.0',
        category: 'devops',
        description: 'GitLab CI/CD pipeline with automated testing, building, and deployment stages.',
        tags: ['gitlab', 'cicd', 'automation', 'deployment'],
        enabled: true,
        configSchema: {
            title: 'GitLab CI/CD Configuration',
            fields: [
                {
                    name: 'stages',
                    label: 'Pipeline Stages',
                    type: 'checkbox',
                    options: [
                        { value: 'test', label: 'Test', default: true },
                        { value: 'build', label: 'Build', default: true },
                        { value: 'security-scan', label: 'Security Scan', default: true },
                        { value: 'deploy-staging', label: 'Deploy to Staging', default: true },
                        { value: 'deploy-prod', label: 'Deploy to Production', default: false }
                    ]
                },
                {
                    name: 'deploymentStrategy',
                    label: 'Deployment Strategy',
                    type: 'radio',
                    options: [
                        { value: 'blue-green', label: 'Blue-Green Deployment' },
                        { value: 'rolling', label: 'Rolling Update' },
                        { value: 'canary', label: 'Canary Deployment' }
                    ],
                    default: 'rolling'
                },
                {
                    name: 'notificationChannel',
                    label: 'Notification Channel',
                    type: 'text',
                    default: '#deployments',
                    description: 'Slack channel for deployment notifications'
                }
            ]
        }
    },
    // Security Plugins
    'sonarqube-analysis': {
        id: 'sonarqube-analysis',
        name: 'SonarQube Code Quality',
        icon: '🔍',
        version: '10.0',
        category: 'security',
        description: 'SonarQube integration for code quality analysis, security vulnerability detection, and technical debt management.',
        tags: ['sonarqube', 'code-quality', 'security', 'static-analysis'],
        enabled: true,
        configSchema: {
            title: 'SonarQube Configuration',
            fields: [
                {
                    name: 'qualityGate',
                    label: 'Quality Gate',
                    type: 'select',
                    options: ['Sonar way', 'Custom', 'Strict'],
                    default: 'Sonar way',
                    description: 'Quality gate to enforce code standards'
                },
                {
                    name: 'coverageThreshold',
                    label: 'Code Coverage Threshold (%)',
                    type: 'range',
                    min: 50,
                    max: 100,
                    default: 80,
                    description: 'Minimum code coverage required to pass quality gate'
                },
                {
                    name: 'includeSecurityHotspots',
                    label: 'Include Security Hotspot Analysis',
                    type: 'checkbox',
                    default: true
                }
            ]
        }
    },
    // Monitoring Plugins
    'prometheus-grafana': {
        id: 'prometheus-grafana',
        name: 'Prometheus + Grafana',
        icon: '📊',
        version: '2.45.0',
        category: 'monitoring',
        description: 'Complete monitoring stack with Prometheus metrics collection and Grafana dashboards.',
        tags: ['prometheus', 'grafana', 'monitoring', 'metrics'],
        enabled: false,
        configSchema: {
            title: 'Monitoring Configuration',
            fields: [
                {
                    name: 'metricsEndpoint',
                    label: 'Metrics Endpoint',
                    type: 'text',
                    default: '/actuator/prometheus',
                    description: 'Endpoint for metrics collection'
                },
                {
                    name: 'retentionPeriod',
                    label: 'Data Retention Period',
                    type: 'select',
                    options: ['7d', '15d', '30d', '90d', '1y'],
                    default: '30d'
                },
                {
                    name: 'alerting',
                    label: 'Enable Alerting',
                    type: 'checkbox',
                    default: true,
                    description: 'Configure alerts for critical metrics'
                }
            ]
        }
    }
};
