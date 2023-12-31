/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}",],
  theme: {
    fontSize: {
      'headline-1': ['26px', {
        lineHeight: '40px',
        fontWeight: 700
      }],
      'headline-2': ['22px', {
        lineHeight: '32px',
        fontWeight: 700
      }],
      'headline-3': ['20px', {
        lineHeight: '32px',
        fontWeight: 700
      }],
      'headline-4': ['18px', {
        lineHeight: '24px',
        fontWeight: 700
      }],
      'headline-5': ['16px', {
        lineHeight: '24px',
        fontWeight: 700
      }],
      'headline-6': ['14px', {
        lineHeight: '20px',
        fontWeight: 700
      }],
      'sub-headline-1': ['16px', {
        lineHeight: '24px',
        fontWeight: 500
      }],
      'body-1': ['16px', {
        lineHeight: '24px',
        fontWeight: 400
      }],
      'body-2': ['14px', {
        lineHeight: '24px',
        fontWeight: 400
      }],
      'body-3': ['12px', {
        lineHeight: '16px',
        fontWeight: 400
      }],
    },
    extend: {
      colors: {
        grey: {
          100: '#FCFCFC',
          200: '#E9E9EA',
          300: '#C5C5C5',
          400: '#91989B',
          500: '#474747',
          600: '#141619',
        },
        blueGrey: {
          100: '#E7ECF0',
          200: '#E2E8EB',
          300: '#D9E1E5',
          400: '#CED5DC',
          500: '#BDC5CD',
          600: '#687784',
          700: '#536471',
        },
        green: {
          100: '#58BC6C',
          200: '#00BA7C',
        },
        yellow: {
          100: '#FFE042',
        },
        red: {
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
    },
  },
  plugins: [],
}

