/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}",],
  theme: {
    fontSize: {
      'headline-1': ['28px', {
        lineHeight: '40px',
        letterSpacing: '-0.04em',
        fontWeight: 700
      }],
      'headline-2': ['26px', {
        lineHeight: '32px',
        letterSpacing: '-0.04em',
        fontWeight: 700
      }],
      'headline-3': ['24px', {
        lineHeight: '32px',
        letterSpacing: '-0.04em',
        fontWeight: 600
      }],
      'headline-4': ['22px', {
        lineHeight: '28px',
        letterSpacing: '-0.04em',
        fontWeight: 700
      }],
      'headline-5': ['20px', {
        lineHeight: '24px',
        letterSpacing: '-0.04em',
        fontWeight: 700
      }],
      'headline-6': ['18px', {
        lineHeight: '20px',
        letterSpacing: '-0.04em',
        fontWeight: 700
      }],
      'headline-7': ['16px', {
        lineHeight: '20px',
        letterSpacing: '-0.04em',
        fontWeight: 700
      }],
      'headline-8': ['14px', {
        lineHeight: '20px',
        letterSpacing: '-0.04em',
        fontWeight: 700
      }],
      'sub-headline-1': ['24px', {
        lineHeight: '20px',
        letterSpacing: '-0.04em',
        fontWeight: 600
      }],
      'sub-headline-2': ['16px', {
        lineHeight: '20px',
        letterSpacing: '-0.04em',
        fontWeight: 500
      }],
      'sub-headline-3': ['14px', {
        lineHeight: '16px',
        letterSpacing: '-0.04em',
        fontWeight: 500
      }],
      'body-1': ['16px', {
        lineHeight: '20px',
        letterSpacing: '-0.02em',
        fontWeight: 400
      }],
      'body-2': ['14px', {
        lineHeight: '20px',
        letterSpacing: '-0.02em',
        fontWeight: 400
      }],
      'body-3': ['12px', {
        lineHeight: '20px',
        letterSpacing: '-0.02em',
        fontWeight: 400
      },],
      'body-4': ['10px', {
        lineHeight: '12px',
        letterSpacing: '-0.02em',
        fontWeight: 400
      },],
    },
    extend: {
      colors: {
        grey: {
          100: '#FCFCFC',
          200: '#E9E9EA',
          300: '#C5C5C5',
          400: '#87898C',
          500: '#474747',
          600: '#141619',
        },
        blueGrey: {
          100: '#E7ECF0',
          200: '#E2E8EB',
          300: '#D9E1E5',
          400: '#CED5DC',
          500: '#BDC5CD',
          600: '#91989B',
          700: '#687784',
          800: '#536471',
        },
        green: {
          100: '#58BC6C',
          200: '#00BA7C',
        },
        yellow: {
          100: '#FFE042',
        },
        red: {
          100: '#F4212E',
        },
        pink: {
          100: '#F91880',
          200: '#CE395F',
        },
        purple: {
          100: '#7856FF',
          200: '#724DBD',
        },
        blue: {
          100: '#E9F5FD',
          200: '#C6E6FB',
          300: '#8ECDF7',
          400: '#4C9EEB',
          500: '#1D9BF0',
        }
      },
      keyframes: {
        'slide-up': {
          from: {
            transform: 'translateY(100%)'
          },
          to: {
            transform: 'translateY(0)'
          }
        }
      },
      animation: {
        'slide-up': 'slide-up 0.15s ease-in'
      }
    },
  },
  plugins: [],
}

