// Định nghĩa kiểu cho quy tắc validation
interface ValidationRule {
    required?: boolean;
    type?: string;
    min?: number;
    max?: number;
    message: string;
    trigger: string;
}

// Hàm validation yêu cầu
export const validateRequired = (): ValidationRule => ({
    required: true,
    message: 'This field is required',  // thông điệp mặc định
    trigger: 'blur',
});

// Hàm validation độ dài
export const validateLength = (min: number, max: number): ValidationRule => ({
    min: min,
    max: max,
    message: `Length must be between ${min} and ${max} characters`, // thông điệp mặc định
    trigger: 'blur',
});

// Hàm validation kiểu số (ví dụ: cho price, age, ...)
export const validateNumber = (min: number, max: number): ValidationRule => ({
    type: 'number',
    min: min,
    max: max,
    message: `Value must be between ${min} and ${max}`, // thông điệp mặc định
    trigger: 'blur',
});

// Hàm validation email
export const validateEmail = (): ValidationRule => ({
    type: 'email',
    message: 'Please enter a valid email', // thông điệp mặc định
    trigger: 'blur',
});